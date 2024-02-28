# smartcrop4j [![tests](https://github.com/sigpwned/smartcrop4j/actions/workflows/tests.yml/badge.svg)](https://github.com/sigpwned/smartcrop4j/actions/workflows/tests.yml) [![Maven Central Version](https://img.shields.io/maven-central/v/com.sigpwned/smartcrop4j)](https://search.maven.org/search?q=g:com.sigpwned%20a:smartcrop4j)


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

    BufferedImage croppedImage=Smartcrop.crop(originalImage, cropWidth, cropHeight);

This will cause the system to choose a crop of the appropriate aspect ratio and then copy the result to a new image, possibly scaling the cropped area up or down depending on the image and crop sizes.

To load an image from a file, crop it, and store the result in a new file with the same image type, all using the default configuration, use:

    File croppedFile=Smartcrop.crop(imageFile, cropWidth, cropHeight);
    try {
        // Do some work...
    }
    finally {
        croppedFile.delete();
    }

To boost the importance of an area of the image for the purposes of crop selection, for example to incorporate information from face detection or object labeling, use:

    BufferedImage croppedImage=Smartcrop.crop(originalImage, cropWidth, cropHeight, List.of(
        new CropBoost(firstBoostX, firstBoostY, firstBoostWidth, firstBoostHeight, 1.0f),
        new CropBoost(secondBoostX, secondBoostY, secondBoostWidth, secondBoostHeight, 0.5f)));
