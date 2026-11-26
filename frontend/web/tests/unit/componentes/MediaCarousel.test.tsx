import { describe, it, expect, beforeEach, vi } from "vitest";
import { render, screen, fireEvent } from "@testing-library/react";
import MediaCarousel from "../../../src/components/MediaCarousel/MediaCarousel";
import type { MultimediaSimpleDTO } from "@shared/models/MultimediaSimpleDTO";
import { APIURL } from "@/config/env";

const multimedia: MultimediaSimpleDTO[] = [
    { id: 1, contentType: "image/png" },
    { id: 2, contentType: "video/mp4" },
    { id: 3, contentType: "image/gif" },
    { id: 4, contentType: "image/jpeg" },
];

describe("MediaCarousel", () => {
    beforeEach(() => {
        // Mock scrollTo, which is not implemented by jsdom
        HTMLElement.prototype.scrollTo = vi.fn();
    });

    /**
     * Returns the carousel container containing the media slides.
     */
    const getCarousel = () => {
        const firstMedia = screen.getAllByAltText("Experience image")[0];

        return firstMedia.parentElement!.parentElement!;
    };

    /**
     * Configures the carousel dimensions and scroll position.
     */
    const configureCarousel = (
        carousel: HTMLElement,
        width = 500,
        scrollLeft = 0
    ) => {
        Object.defineProperty(carousel, "clientWidth", {
            configurable: true,
            value: width,
        });

        Object.defineProperty(carousel, "scrollLeft", {
            configurable: true,
            value: scrollLeft,
        });
    };

    describe("Rendering", () => {
        it("should render nothing when multimedia is empty", () => {
            const { container } = render(
                <MediaCarousel multimedia={[]} />
            );

            expect(container.firstChild).toBeNull();
        });

        it("should render all images and videos", () => {
            const { container } = render(
                <MediaCarousel multimedia={multimedia} />
            );

            const images = screen.getAllByAltText("Experience image");
            const videos = container.querySelectorAll("video");

            expect(images).toHaveLength(3);
            expect(videos).toHaveLength(1);
        });

        it("should render images with their correct URLs", () => {
            render(
                <MediaCarousel multimedia={multimedia} />
            );

            const images = screen.getAllByAltText("Experience image");

            expect(images[0]).toHaveAttribute(
                "src",
                `${APIURL}/multimedia/experiences/1`
            );

            expect(images[1]).toHaveAttribute(
                "src",
                `${APIURL}/multimedia/experiences/3`
            );

            expect(images[2]).toHaveAttribute(
                "src",
                `${APIURL}/multimedia/experiences/4`
            );
        });

        it("should render videos with their correct URLs and controls", () => {
            const { container } = render(
                <MediaCarousel multimedia={multimedia} />
            );

            const video = container.querySelector("video");

            expect(video).toBeInTheDocument();

            expect(video).toHaveAttribute(
                "src",
                `${APIURL}/multimedia/experiences/2`
            );

            expect(video).toHaveAttribute("controls");
            expect(video).toHaveAttribute("playsinline");
            expect(video).toHaveAttribute("preload", "metadata");
        });

        it("should use the default alt text for images", () => {
            render(
                <MediaCarousel
                    multimedia={[
                        { id: 1, contentType: "image/png" },
                    ]}
                />
            );

            expect(
                screen.getByAltText("Experience image")
            ).toBeInTheDocument();
        });

        it("should display the initial media counter", () => {
            render(
                <MediaCarousel multimedia={multimedia} />
            );

            expect(
                screen.getByText("1 / 4")
            ).toBeInTheDocument();
        });

        it("should render a single media correctly", () => {
            render(
                <MediaCarousel
                    multimedia={[
                        { id: 1, contentType: "image/png" },
                    ]}
                />
            );

            expect(
                screen.getByAltText("Experience image")
            ).toBeInTheDocument();

            expect(
                screen.getByText("1 / 1")
            ).toBeInTheDocument();
        });
    });

    describe("Navigation", () => {
        it("should not render the previous button initially", () => {
            render(
                <MediaCarousel multimedia={multimedia} />
            );

            expect(
                screen.queryByRole("button", {
                    name: "Previous media",
                })
            ).not.toBeInTheDocument();
        });

        it("should render the next button initially", () => {
            render(
                <MediaCarousel multimedia={multimedia} />
            );

            expect(
                screen.getByRole("button", {
                    name: "Next media",
                })
            ).toBeInTheDocument();
        });

        it("should scroll to the next media when next button is clicked", () => {
            render(
                <MediaCarousel multimedia={multimedia} />
            );

            const carousel = getCarousel();

            configureCarousel(carousel);

            fireEvent.click(
                screen.getByRole("button", {
                    name: "Next media",
                })
            );

            expect(carousel.scrollTo).toHaveBeenCalledWith({
                left: 500,
                behavior: "smooth",
            });
        });

        it("should scroll to the previous media when previous button is clicked", () => {
            render(
                <MediaCarousel multimedia={multimedia} />
            );

            const carousel = getCarousel();

            configureCarousel(carousel, 500, 500);

            // Simulate being on the second media
            fireEvent.scroll(carousel);

            fireEvent.click(
                screen.getByRole("button", {
                    name: "Previous media",
                })
            );

            expect(carousel.scrollTo).toHaveBeenCalledWith({
                left: 0,
                behavior: "smooth",
            });
        });

        it("should update the counter when scrolling to the next media", () => {
            render(
                <MediaCarousel multimedia={multimedia} />
            );

            const carousel = getCarousel();

            configureCarousel(carousel, 500, 500);

            fireEvent.scroll(carousel);

            expect(
                screen.getByText("2 / 4")
            ).toBeInTheDocument();
        });

        it("should update the counter when scrolling to the last media", () => {
            render(
                <MediaCarousel multimedia={multimedia} />
            );

            const carousel = getCarousel();

            configureCarousel(carousel, 500, 1500);

            fireEvent.scroll(carousel);

            expect(
                screen.getByText("4 / 4")
            ).toBeInTheDocument();
        });

        it("should render the previous button after scrolling forward", () => {
            render(
                <MediaCarousel multimedia={multimedia} />
            );

            const carousel = getCarousel();

            configureCarousel(carousel, 500, 500);

            fireEvent.scroll(carousel);

            expect(
                screen.getByRole("button", {
                    name: "Previous media",
                })
            ).toBeInTheDocument();
        });

        it("should not render the next button on the last media", () => {
            render(
                <MediaCarousel multimedia={multimedia} />
            );

            const carousel = getCarousel();

            configureCarousel(carousel, 500, 1500);

            fireEvent.scroll(carousel);

            expect(
                screen.queryByRole("button", {
                    name: "Next media",
                })
            ).not.toBeInTheDocument();
        });

        it("should not render navigation buttons when there is a single media", () => {
            render(
                <MediaCarousel
                    multimedia={[
                        { id: 1, contentType: "image/png" },
                    ]}
                />
            );

            expect(
                screen.queryByRole("button", {
                    name: "Previous media",
                })
            ).not.toBeInTheDocument();

            expect(
                screen.queryByRole("button", {
                    name: "Next media",
                })
            ).not.toBeInTheDocument();

            expect(
                screen.getByText("1 / 1")
            ).toBeInTheDocument();
        });
    });
});