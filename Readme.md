#Notes on uploaded images

A few things happen to uploaded images.  First, we scale the image down to a mazimum width of 600px, which hopefully also
reduces the file size.  This 600px standard image is then transfered to cloudinary, so we can request on-the-fly
transformation for both public pages and management pages.  We also store it in amazon s3,
just in case we want to transfer images out of cloudinary without paying their costs.


### Notes on Adoption and Foster Records.

- When a pet is transfered to another organization, does all of it's records get transfered as well? At the moment,
if we switch the organization a pet belongs to, the new organization will see the exact same thing the old
organization saw before the transfer, and the old one will see nothing.
- As above, the same goes for history.  Once the pet is transfered, the new organization will see all of the "Updated
 by Charlie" and "Bobby uploaded a pic of Champ".
