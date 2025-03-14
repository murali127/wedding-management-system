// Initialize all carousels on page load
document.addEventListener('DOMContentLoaded', function () {
    // Banner Carousel
    const bannerCarousel = new bootstrap.Carousel(document.getElementById('bannerCarousel'), {
        interval: 3000, // Slide every 3 seconds
        ride: 'carousel' // Auto-start
    });

    // Testimonial Carousel
    const testimonialCarousel = new bootstrap.Carousel(document.getElementById('testimonialCarousel'), {
        interval: 3000, // Slide every 3 seconds
        ride: 'carousel' // Auto-start
    });

    // Gallery Carousel
    const galleryCarousel = new bootstrap.Carousel(document.getElementById('galleryCarousel'), {
        interval: 3000, // Slide every 3 seconds
        ride: 'carousel' // Auto-start
    });

    console.log("BlissfulKnots Loaded Successfully! All carousels initialized.");
});