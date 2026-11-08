
import { render, act} from "@testing-library/react";
import { beforeEach, describe, expect, it, vi } from "vitest";
import type { MapMarker } from "@shared/models/MapMarker";

import MapComponent from "../../../src/components/Map/MapComponent";

const mapLibreMocks = vi.hoisted(() => ({
    mapInstances: [] as any[],
    markerInstances: [] as any[],
    popupInstances: [] as any[],
    styleLoaded: true,
}));

vi.mock("maplibre-gl", () => {
    class MockMap {
        options: any;
        styleLoaded: boolean;
        listeners: Record<string, () => void> = {};

        jumpTo = vi.fn();
        remove = vi.fn();

        isStyleLoaded = vi.fn(() => this.styleLoaded);

        once = vi.fn((event: string, callback: () => void) => {
            this.listeners[event] = callback;
            return this;
        });

        off = vi.fn((event: string, callback: () => void) => {
            if (this.listeners[event] === callback) {
                delete this.listeners[event];
            }
            return this;
        });

        constructor(options: any) {
            this.options = options;
            this.styleLoaded = mapLibreMocks.styleLoaded;
            mapLibreMocks.mapInstances.push(this);
        }

        emit(event: string) {
            const callback = this.listeners[event];

            if (callback) {
                delete this.listeners[event];
                callback();
            }
        }
    }

    class MockMarker {
        options: any;
        coordinates: number[] | undefined;
        popup: any;

        setLngLat = vi.fn((coordinates: number[]) => {
            this.coordinates = coordinates;
            return this;
        });

        setPopup = vi.fn((popup: any) => {
            this.popup = popup;
            return this;
        });

        addTo = vi.fn(() => this);
        remove = vi.fn();

        constructor(options?: any) {
            this.options = options;
            mapLibreMocks.markerInstances.push(this);
        }
    }

    class MockPopup {
        text: string | undefined;

        setText = vi.fn((text: string) => {
            this.text = text;
            return this;
        });

        constructor() {
            mapLibreMocks.popupInstances.push(this);
        }
    }

    return {
        Map: MockMap,
        Marker: MockMarker,
        Popup: MockPopup,
        setWorkerUrl: vi.fn(),
    };
});

vi.mock("maplibre-gl/dist/maplibre-gl-worker.mjs?worker&url", () => ({
    default: "mock-worker-url",
}));

// Adapt the import path to your project.

const createMark = (
    latitude: number,
    longitude: number,
    title: string
): MapMarker => ({
    latitude,
    longitude,
    title,
} as MapMarker);

const defaultProps = {
    latitude: 40.4168,
    longitude: -3.7038,
    zoom: 10,
    marks: [] as MapMarker[],
    markerType: "cities" as const,
};

describe("MapComponent", () => {
    beforeEach(() => {
        mapLibreMocks.mapInstances.length = 0;
        mapLibreMocks.markerInstances.length = 0;
        mapLibreMocks.popupInstances.length = 0;
        mapLibreMocks.styleLoaded = true;
    });

    it("should initialize the map with the provided configuration", () => {
        const { container } = render(
            <MapComponent {...defaultProps} />
        );

        expect(mapLibreMocks.mapInstances).toHaveLength(1);

        const map = mapLibreMocks.mapInstances[0];

        expect(map.options).toEqual({
            container: container.firstChild,
            style: "/map-styles/my-erasmus-map-styles.json",
            center: [-3.7038, 40.4168],
            zoom: 10,
        });
    });

    it("should update the map position when coordinates or zoom change", () => {
        const { rerender } = render(
            <MapComponent {...defaultProps} />
        );

        const map = mapLibreMocks.mapInstances[0];

        map.jumpTo.mockClear();

        rerender(
            <MapComponent {...defaultProps} latitude={48.8566} longitude={2.3522} zoom={12}/>
        );

        expect(map.jumpTo).toHaveBeenCalledWith({
            center: [2.3522, 48.8566],
            zoom: 12,
        });
    });

    it("should create popup markers for cities", () => {
        const marks = [
            createMark(40.4168, -3.7038, "Madrid"),
        ];

        render(
            <MapComponent {...defaultProps} marks={marks} markerType="cities"/>
        );

        expect(mapLibreMocks.markerInstances).toHaveLength(1);
        expect(mapLibreMocks.popupInstances).toHaveLength(1);

        const marker = mapLibreMocks.markerInstances[0];
        const popup = mapLibreMocks.popupInstances[0];

        expect(marker.setLngLat).toHaveBeenCalledWith([-3.7038, 40.4168]);
        expect(popup.setText).toHaveBeenCalledWith("Madrid");
        expect(marker.setPopup).toHaveBeenCalledWith(popup);
        expect(marker.addTo).toHaveBeenCalledWith(
            mapLibreMocks.mapInstances[0]
        );
    });

    it("should create popup markers for experiences", () => {
        const marks = [
            createMark(48.8566, 2.3522, "My Erasmus experience"),
        ];

        render(
            <MapComponent
                {...defaultProps}
                marks={marks}
                markerType="experiences"
            />
        );

        expect(mapLibreMocks.markerInstances).toHaveLength(1);
        expect(mapLibreMocks.popupInstances).toHaveLength(1);

        expect(
            mapLibreMocks.popupInstances[0].setText
        ).toHaveBeenCalledWith("My Erasmus experience");
    });

    it("should create circular markers for preview", () => {
        const marks = [
            createMark(48.8566, 2.3522, "Preview city"),
        ];

        render(
            <MapComponent
                {...defaultProps}
                marks={marks}
                markerType="preview"
            />
        );

        expect(mapLibreMocks.markerInstances).toHaveLength(1);
        expect(mapLibreMocks.popupInstances).toHaveLength(0);

        const marker = mapLibreMocks.markerInstances[0];

        expect(marker.options.element).toBeInstanceOf(HTMLDivElement);
        expect(marker.options.element.className).toBe(
            "h-4 w-4 rounded-full bg-[#4A90D9] border-2 border-white shadow-md cursor-pointer"
        );

        expect(marker.setLngLat).toHaveBeenCalledWith([2.3522, 48.8566]);
        expect(marker.addTo).toHaveBeenCalled();
    });

    it("should wait for the map style to load before creating markers", () => {
        mapLibreMocks.styleLoaded = false;

        const marks = [
            createMark(40.4168, -3.7038, "Madrid"),
        ];

        render(
            <MapComponent
                {...defaultProps}
                marks={marks}
            />
        );

        const map = mapLibreMocks.mapInstances[0];

        expect(map.once).toHaveBeenCalledWith(
            "load",
            expect.any(Function)
        );

        expect(mapLibreMocks.markerInstances).toHaveLength(0);

        act(() => {
            map.emit("load");
        });

        expect(mapLibreMocks.markerInstances).toHaveLength(1);
    });

    it("should remove previous markers when marks change", () => {
        const firstMarks = [
            createMark(40.4168, -3.7038, "Madrid"),
        ];

        const secondMarks = [
            createMark(48.8566, 2.3522, "Paris"),
            createMark(52.5200, 13.4050, "Berlin"),
        ];

        const { rerender } = render(
            <MapComponent
                {...defaultProps}
                marks={firstMarks}
            />
        );

        const previousMarker = mapLibreMocks.markerInstances[0];

        rerender(
            <MapComponent
                {...defaultProps}
                marks={secondMarks}
            />
        );

        expect(previousMarker.remove).toHaveBeenCalledTimes(1);
        expect(mapLibreMocks.markerInstances).toHaveLength(3);

        expect(mapLibreMocks.markerInstances[1].setLngLat)
            .toHaveBeenCalledWith([2.3522, 48.8566]);

        expect(mapLibreMocks.markerInstances[2].setLngLat)
            .toHaveBeenCalledWith([13.4050, 52.5200]);
    });

    it("should remove markers and destroy the map when unmounted", () => {
        const marks = [
            createMark(40.4168, -3.7038, "Madrid"),
            createMark(48.8566, 2.3522, "Paris"),
        ];

        const { unmount } = render(
            <MapComponent
                {...defaultProps}
                marks={marks}
            />
        );

        const map = mapLibreMocks.mapInstances[0];
        const markers = [...mapLibreMocks.markerInstances];

        unmount();

        markers.forEach(marker => {
            expect(marker.remove).toHaveBeenCalledTimes(1);
        });

        expect(map.remove).toHaveBeenCalledTimes(1);
    });
});
