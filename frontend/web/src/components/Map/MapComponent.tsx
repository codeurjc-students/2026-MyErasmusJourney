import { Map, Marker, Popup, setWorkerUrl } from "maplibre-gl";
import "maplibre-gl/dist/maplibre-gl.css";
import workerUrl from "maplibre-gl/dist/maplibre-gl-worker.mjs?worker&url";
import { useEffect, useRef } from "react";
import type { MapMarker } from "@shared/models/MapMarker";

interface MapProps {
    latitude: number;
    longitude: number;
    zoom: number;
    marks: MapMarker[];
    markerType: "cities" | "preview" | "experiences";
}

setWorkerUrl(workerUrl);

export default function MapComponent({
    latitude,
    longitude,
    zoom,
    marks,
    markerType,
}: MapProps) {
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
                marker = createPopUpMarker(map, mark);
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

    function createPopUpMarker(map: Map, mark: MapMarker): Marker {
        return new Marker()
            .setLngLat([mark.longitude, mark.latitude])
            .setPopup(new Popup().setText(mark.title ?? ""))
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
        return createPopUpMarker(map, mark);
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
