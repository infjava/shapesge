# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Disabling hiding the ShapesGE window, as the window cannot be shown again.
- image resource can be loaded once and used in multiple `Image`/`Obrazok` instances
  using `ImageData`/`DataObrazku` classes
- Every shape can be moved to specific position by using `changePosition`/`zmenPolohu`.
  Important: this method specifies the position of anchor point the same way
  as using the constructor does instead of specifying the position of the center
  as it does for `Obrazok` class in the original tvary project.

### Changed
- `Text` class renamed to `TextBlock` (english API) and `BlokTextu` (slovak API) to 
  differentiate names between localizations
- `FontStyle` enum renamed to `StylFontu` in the slovak API
- `ExitOnClose` configuration option replaced by `OnClose` = `hide`/`exit`/`nothing`/`send` message.

### Fixed
- key events with key modifiers are parsed correctly
- some comment fixes 

### Other changes
- big refactoring to decouple public API from actual implementation.
  This allows us to merge both slovak and english APIs to the one
  branch.
- fixed javadoc documentation

## [1.2.0] - 2023-03-13

### Added
- It is now possible to show multiline text
- Images can be configured to load from resources

### Fixed
- Mouse move events works correctly
- Shapes no longer stays invisible and accepts commands to make visible it again

### Other Changes
- Checkstyle fixes

## [1.1.0] - 2022-11-28

### Added
- possibility to render text

### Fixed
- look for a config in working directory too as BlueJ is clearly not setting ClassPath correctly

## [1.0.1] - 2022-11-15

### Fixed
- look for a config in working directory too as BlueJ is clearly not setting ClassPath correctly

## [1.0.0] - 2022-11-15

First release
