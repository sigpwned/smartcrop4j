# smartcrop4j [![tests](https://github.com/sigpwned/smartcrop4j/actions/workflows/tests.yml/badge.svg)](https://github.com/sigpwned/smartcrop4j/actions/workflows/tests.yml)

Content-aware image cropping for Java 11+ based on the excellent [@jwagner/smartcrop.js](https://github.com/jwagner/smartcrop.js).

## Functionality

![Example Crop](https://camo.githubusercontent.com/546b58d72105eb415bb310d65a5c0a1682aeffc5ff66ae687c50443962f0be72/68747470733a2f2f3239612e63682f73616e64626f782f323031342f736d61727463726f702f6578616d706c652e6a7067)
Image: [https://www.flickr.com/photos/endogamia/5682480447/](https://www.flickr.com/photos/endogamia/5682480447/) by N. Feans, via [@jwagner/smartcrop.js](https://github.com/jwagner/smartcrop.js).

Given a `BufferedImage`, `smartcrop4j` considers the following heuristics to recommend an "interesting" crop with a given aspect ratio from the original `BufferedImage`:

* Edge detection
* Regions of high saturation
* Regions rich in skin-like colors
* Composition best practices, e.g., [Rule of thirds](https://en.wikipedia.org/wiki/Rule_of_thirds)

## Quickstart

To produce a crop of an image of a given width and height with the default configuration, simply use:

    public BufferedImage crop(BufferedImage originalImage, int cropWidth, int cropHeight) {
        return Smartcrop.crop(originalImage, width, height);
    }

This will cause the system to choose a crop of the appropriate aspect ratio and then copy the result to a new image, possibly scaling the cropped area up or down depending on the image and crop sizes.

To load an image from a file, crop it, and store the result in a new file with the same image type, all using the default configuration, use:

    public File crop(File imageFile, int cropWidth, int cropHeight) throws IOException {
        Smartcrop.crop(imageFile, cropWidth, cropHeight);
    }
