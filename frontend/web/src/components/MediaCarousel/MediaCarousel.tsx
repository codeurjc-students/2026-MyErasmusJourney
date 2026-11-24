import { APIURL } from "@/config/env";
import type { MultimediaSimpleDTO } from "@shared/models/MultimediaSimpleDTO";
import { useRef, useState } from "react";

interface MediaCarouselProps{
    multimedia: MultimediaSimpleDTO[];
}

export default function MediaCarousel({ multimedia } : MediaCarouselProps) {
    const carouselRef = useRef<HTMLDivElement>(null);
    const [currentIndex, setCurrentIndex] = useState(0);

    const scrollToMedia = (index:number) => {
        const carousel = carouselRef.current;

        if (!carousel) return;

        carousel.scrollTo({
            left: carousel.clientWidth * index,
            behavior: "smooth",
        });
    };

    const handleScroll = () => {
        const carousel = carouselRef.current;

        if (!carousel) return;

        const index = Math.round(carousel.scrollLeft / carousel.clientWidth);

        setCurrentIndex(index);
    };

    const previous = () => {
        scrollToMedia(Math.max(currentIndex - 1, 0));
    };

    const next = () => {
        scrollToMedia(Math.min(currentIndex + 1, multimedia.length - 1));
    };

    if (!multimedia.length) return null;

    return (
        <div className="relative w-full max-w-full overflow-hidden rounded-xl bg-black">
            {/* Multimedia container */}
            <div ref={carouselRef} onScroll={handleScroll} className="flex w-full overflow-x-auto snap-x snap-mandatory scroll-smooth">
                {multimedia.map((media) => (
                    <div key={media.id} className="flex min-w-full max-w-full shrink-0 snap-center items-center justify-center aspect-video">
                        {media.contentType.includes("image") ? (
                            <img src={`${APIURL}/multimedia/experiences/${media.id}`} alt={"Experience image"} className="h-full w-full object-contain"/>
                        ) : (
                            <video src={`${APIURL}/multimedia/experiences/${media.id}`} controls playsInline preload="metadata" className="h-full w-full object-contain"/>
                        )}
                    </div>
                ))}
            </div>

            {/* Previous button */}
            {currentIndex > 0 && (
                <button type="button" onClick={previous} aria-label="Previous media" className="absolute left-2 top-1/2 -translate-y-1/2 rounded-full bg-black/50 p-2 text-white hover:bg-black/75">
                    &#10094;
                </button>
            )}

            {/* Next button */}
            {currentIndex < multimedia.length - 1 && (
                <button type="button" onClick={next} aria-label="Next media" className="absolute right-2 top-1/2 -translate-y-1/2 rounded-full bg-black/50 p-2 text-white hover:bg-black/75">
                    &#10095;
                </button>
            )}

            {/* Media counter */}
            <div className="absolute bottom-2 right-2 rounded-full bg-black/60 px-3 py-1 text-sm text-white">
                {currentIndex + 1} / {multimedia.length}
            </div>
        </div>
    );
}