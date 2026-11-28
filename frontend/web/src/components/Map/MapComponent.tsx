import { Map, Marker, Popup, setWorkerUrl } from "maplibre-gl";
import "maplibre-gl/dist/maplibre-gl.css";
import workerUrl from "maplibre-gl/dist/maplibre-gl-worker.mjs?worker&url";
import { useEffect, useRef } from "react";
import type { MapMarker } from "@shared/models/MapMarker";
import { useNavigate, type NavigateFunction } from "react-router-dom";

interface MapProps {
    latitude: number;
    longitude: number;
    zoom: number;
    marks: MapMarker[];
    markerType: "cities" | "preview" | "experiences";
}

setWorkerUrl(workerUrl);

export default function MapComponent({ latitude, longitude, zoom, marks, markerType }: MapProps) {

    const navigate = useNavigate();

    const mapContainer = useRef<HTMLDivElement>(null);
    const mapRef = useRef<Map | null>(null);
    const markersRef = useRef<Marker[]>([]);

    const createMarker = (map: Map, mark: MapMarker): Marker => {
        let marker: Marker;
        switch (markerType) {
            case "preview":
                marker = createCircleMarker(map, mark);
                break;
            case "cities":
                marker = createPopUpMarker(map, mark, navigate);
                break;
            case "experiences":
                marker = createPinMarker(map, mark);
                break;
        }
        const element = marker.getElement();
        element.dataset.testid = "map-marker";
        element.dataset.markerType = markerType;
        element.setAttribute("aria-label", mark.title ?? "");
        return marker;
    };

    function createPopUpMarker(map: Map, mark: MapMarker, navigate: NavigateFunction): Marker {
        // Calculate rating color
        const rating = Math.min(10, Math.max(0, Number(mark.rating ?? 0)));
        const red = Math.round((1 - rating / 10) * 255);
        const green = Math.round((rating / 10) * 255);
        const ratingColor = `rgb(${red}, ${green}, 0)`;

        // Main popup container
        const container = document.createElement("div");
        container.className = "inline-flex w-fit max-w-64 flex-col gap-2";

        // Header: title + rating
        const header = document.createElement("div");
        header.className = "flex flex-row flex-nowrap items-center gap-3 w-full";

        // Title
        const title = document.createElement("h4");
        title.className = "m-0 min-w-0 flex-1 break-words text-[0.85rem] font-bold text-[#1E3A5F]";
        title.textContent = mark.title;

        // Rating
        const ratingBadge = document.createElement("div");
        ratingBadge.className = "flex items-center justify-center shrink-0 rounded-xl px-2.5 py-2 text-sm font-bold text-white";
        ratingBadge.style.backgroundColor = ratingColor;
        ratingBadge.textContent = rating.toFixed(1);

        // Navigation link
        const link = document.createElement("a");
        link.className = "w-fit text-sm font-medium text-[#4A90C2] underline hover:text-[#1E3A5F]";
        link.textContent = "Explore it →";
        link.href = mark.link;

        link.addEventListener("click", (event) => {
            event.preventDefault();
            navigate(mark.link);
        });

        // Assemble popup content
        header.append(title, ratingBadge);
        container.append(header, link);

        // Popup configuration
        const popup = new Popup({
            closeButton: false,
            closeOnClick: true,
            maxWidth: "none",
            className: "my-erasmus-popup"
        }).setDOMContent(container);

        // Create marker
        return new Marker()
            .setLngLat([mark.longitude, mark.latitude])
            .setPopup(popup)
            .addTo(map);
    }

    function createCircleMarker(map: Map, mark: MapMarker): Marker {
        const element = document.createElement("div");

        element.className =
            "h-4 w-4 rounded-full bg-[#4A90D9] border-2 border-white shadow-md cursor-pointer";

        return new Marker({ element })
            .setLngLat([mark.longitude, mark.latitude])
            .addTo(map);
    }

    function createPinMarker(map: Map, mark: MapMarker): Marker {
        // Pending of customization, for now we will use the same as the popup marker
        return createPopUpMarker(map, mark, navigate);
    }

    // Initialize map
    useEffect(() => {
        if (!mapContainer.current) return;

        const map = new Map({
            container: mapContainer.current,
            style: "/map-styles/my-erasmus-map-styles.json",
            center: [longitude, latitude],
            zoom,
        });

        mapRef.current = map;

        return () => {
            markersRef.current.forEach((marker) => marker.remove());
            markersRef.current = [];

            map.remove();
            mapRef.current = null;
        };
    }, []);

    // Update map when props change
    useEffect(() => {
        mapRef.current?.jumpTo({
            center: [longitude, latitude],
            zoom,
        });
    }, [latitude, longitude, zoom]);

    // Update map markers
    // Markers effect: no style gate
    useEffect(() => {
        const map = mapRef.current;
        if (!map) return;

        markersRef.current.forEach((marker) => marker.remove());
        markersRef.current = marks.map((mark) => createMarker(map, mark));
    }, [marks, markerType]);

    // Container with test hooks
    return (
        <div
            ref={mapContainer}
            data-testid="map"
            data-latitude={latitude}
            data-longitude={longitude}
            data-zoom={zoom}
            data-marker-count={marks.length}
            className="w-full h-[40vh] min-h-64 md:h-[50vh] lg:h-[60vh] rounded-xl overflow-hidden"
        />
    );
}
