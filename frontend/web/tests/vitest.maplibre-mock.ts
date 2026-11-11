import { vi } from "vitest";

vi.mock("maplibre-gl", () => {
    class MockMap {
        options?: unknown;
        private handlers: Record<string, Array<(e?: unknown) => void>> = {};

        constructor(options?: unknown) {
            this.options = options;
            // fire "load" asynchronously so components that wait for it keep working
            setTimeout(() => this.handlers["load"]?.forEach((cb) => cb()), 0);
        }

        on(event: string, cb: (e?: unknown) => void) {
            (this.handlers[event] ??= []).push(cb);
            return this;
        }
        off = vi.fn().mockReturnThis();
        once = vi.fn().mockReturnThis();
        addControl = vi.fn().mockReturnThis();
        addSource = vi.fn().mockReturnThis();
        addLayer = vi.fn().mockReturnThis();
        getSource = vi.fn();
        flyTo = vi.fn().mockReturnThis();
        jumpTo = vi.fn().mockReturnThis();
        easeTo = vi.fn().mockReturnThis();
        fitBounds = vi.fn().mockReturnThis();
        setCenter = vi.fn().mockReturnThis();
        setZoom = vi.fn().mockReturnThis();
        resize = vi.fn();
        remove = vi.fn();
    }

    class MockMarker {
        setLngLat = vi.fn().mockReturnThis();
        setPopup = vi.fn().mockReturnThis();
        addTo = vi.fn().mockReturnThis();
        remove = vi.fn();
        getElement = vi.fn(() => document.createElement("div"));
    }

    class MockPopup {
        setLngLat = vi.fn().mockReturnThis();
        setHTML = vi.fn().mockReturnThis();
        setText = vi.fn().mockReturnThis();
        addTo = vi.fn().mockReturnThis();
        remove = vi.fn();
    }

    class MockLngLat {
        lng: number;
        lat: number;
        constructor(lng: number, lat: number) {
            this.lng = lng;
            this.lat = lat;
        }
        toArray() {
            return [this.lng, this.lat];
        }
    }

    class MockLngLatBounds {
        extend = vi.fn(() => this);
        isEmpty = vi.fn(() => false);
    }


    class MockNavigationControl { }
    
    const noop = vi.fn();


    const mod = {
        Map: MockMap,
        Marker: MockMarker,
        Popup: MockPopup,
        NavigationControl: MockNavigationControl,
        LngLat: MockLngLat,
        LngLatBounds: MockLngLatBounds,

        // global config functions
        setWorkerUrl: noop,
        setWorkerCount: noop,
        setMaxParallelImageRequests: noop,
        setRTLTextPlugin: noop,
        addProtocol: noop,
        removeProtocol: noop,
        prewarm: noop,
        clearPrewarmedResources: noop,
    };

    return { ...mod, default: mod };
});