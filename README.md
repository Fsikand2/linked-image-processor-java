# linked-image-processor-java
A Java image-processing project using a custom two-dimensional linked structure, with horizontal and vertical traversal, border manipulation, duplicate row and column removal, and grayscale image file handling.

# Linked Image Processor

**Language:** Java
**Focus:** Linked data structures, iterators, generics, and image processing

This project represents an image as a two-dimensional grid of linked nodes. Each node stores a pixel value and references its neighbors above, below, left, and right.

The implementation supports image traversal, structural modifications, and loading and saving grayscale images in text-based PGM format.

## Key Features

* Custom two-dimensional linked structure for storing image pixels.
* Horizontal and vertical traversal through Java iterators.
* Row insertion and column removal.
* Border creation by copying edge pixels, plus border removal.
* Removal of adjacent identical rows and columns to reduce image dimensions.
* A neighborhood maximum filter that produces a new image.
* Loading and saving text-based PGM (`P2`) images.

## Technical Highlights

* Generic `Image` and `Node` classes using comparable values.
* Custom implementations of Java’s `Iterable` and `Iterator` interfaces.
* Neighbor-link updates during row, column, and border operations.
* File input and output using `Scanner` and `PrintWriter`.

## Project Files

* `Image.java`: Image structure and processing operations.
* `Node.java`: Pixel values and four-directional links.
* `ImageIterator.java`: Horizontal and vertical traversal.
* `Direction.java`: Traversal direction definitions.
* `Utilities.java`: PGM image loading and saving.
* `P2.java`: Provided debugging driver.

## How to Run

With a Java Development Kit installed, place all six Java files in the same folder and compile:

```bash
javac Direction.java Node.java ImageIterator.java Image.java Utilities.java P2.java
```

Run the debugging driver with input and output filenames:

```bash
java P2 input.pgm output.pgm
```

For a small example, save the following as `input.pgm`:

```text
P2
3 3
255
10 20 30
40 50 60
70 80 90
```

The driver loads the image, prints its values using both traversal directions, changes the first pixel to 0, and saves the output. Other image operations are available through the `Image` class but are not demonstrated by this driver.

## Current Limitations

* The loader expects a simple PGM layout without comments: `P2`, dimensions, maximum value, then pixel values.
* Saving always writes a maximum pixel value of 255.
* The maximum filter needs an edge-handling correction: at the top and left boundaries, its search window can extend beyond the intended immediate neighborhood.
* Removing duplicate rows or columns changes image dimensions; it is not reversible file compression.
