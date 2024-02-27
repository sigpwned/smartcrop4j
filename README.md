# smartcrop4j

Content-aware image cropping for Java based on the excellent [@jwagner/smartcrop.js](https://github.com/jwagner/smartcrop.js).

## Functionality

![Example Crop](https://camo.githubusercontent.com/546b58d72105eb415bb310d65a5c0a1682aeffc5ff66ae687c50443962f0be72/68747470733a2f2f3239612e63682f73616e64626f782f323031342f736d61727463726f702f6578616d706c652e6a7067)
Image: [https://www.flickr.com/photos/endogamia/5682480447/](https://www.flickr.com/photos/endogamia/5682480447/) by N. Feans, via [@jwagner/smartcrop.js](https://github.com/jwagner/smartcrop.js).

Given a `BufferedImage`, `smartcrop4j` considers the following heuristics to recommend an "interesting" crop with a given aspect ratio from the original `BufferedImage`:

* Edge detection
* Regions of high saturation
* Regions rich in skin-like colors
* [Rule of thirds](https://en.wikipedia.org/wiki/Rule_of_thirds)

## Quickstart

To crop a loaded image, simply use:

    public BufferedImage crop(BufferedImage originalImage) {
        return Smartcrop.crop(originalImage, width, height);
    }

To load an image from a file, crop it, and store the result in a new file with the same image type, use:

    public File crop(File originalImageFile) throws IOException {
        String imageFileExtension=Optional.ofNullable(originalImageFile.getName())
            .filter(f -> f.contains("."))
            .map(f -> f.substring(f.lastIndexOf(".") + 1))
            .orElseThrow(() -> new IllegalArgumentException("File must have extension"));

        BufferedImage originalImage;
        ImageInputStream input = ImageIO.createImageInputStream(originalImageFile);
        if(input == null)
            throw new IllegalArgumentException("File extension "+imageFileExtension+" is not a recognized file format");
        try (input) {
            originalImage = ImageIO.read(input);
        }

        BufferedImage croppedImage=Smartcrop.crop(originalImage, width, height);

        File result = null;
        File croppedImageFile=File.createTempFile("cropped.", "."+imageFileExtension);
        try {
            ImageOutputStream output=ImageIO.createImageOutputStream(croppedImageFile);
            if(output == null)
                throw new IOException("Failed to write file format "+imageFileExtension);
            try (output) {
                ImageIO.write(output, croppedImage);
            }
            result = croppedImageFile;
        }
        finally {
            if(result == null)
                croppedImageFile.delete();
        }

        return result;
    }
