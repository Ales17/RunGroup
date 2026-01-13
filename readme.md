Project based on [Rungroop-Java](https://github.com/teddysmithdev/RunGroop-Java) created by Teddy Smith.

## Improvements and Changes

### Admin Controller

- Integrated HTMX for enhanced user interactions
- Implemented functionality for editing user details (w/o password)

### CRUD Authorization

- Applied `@PreAuthorize` annotations for method-level security
- Developed a `SecurityService` class to manage authorization logic

### Spring Security Configuration

- Updated configuration to align with the latest Spring Security version

### Custom Error Handling

- Created a custom error controller
- Designed a custom error template for better user experience

### Social Features

- Added a social feature on the root page where users can create posts and like others' posts. HTMX was used to enable dynamic and seamless user interactions.

### File Controller

- Implemented a file controller allowing users to upload images to clubs or events, supporting better visual content management for organizers and administrators.
