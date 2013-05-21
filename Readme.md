#Notes on uploaded images

A few things happen to uploaded images.  First, we scale the image down to a mazimum width of 600px, which hopefully also
reduces the file size.  This 600px standard image is then transfered to cloudinary, so we can request on-the-fly
transformation for both public pages and management pages.  We also store it in amazon s3,
just in case we want to transfer images out of cloudinary without paying their costs.


