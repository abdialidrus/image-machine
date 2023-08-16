Image Machine App
==================

**The development of this project is intended as a test material in the recruitment process at ProSpace**

**Image Machine** is an app built entirely with Kotlin and Jetpack Compose. It
follows Android design and development best practices.

## Features

**Image Machine** displays locally stored Machines data. Users can add new Machines, as well as updating and deleting. **Image Machine** also provides a QR code scanner as the Machines search alternative.

## Tech stack & Open-source libraries
- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Jetpack
  - Compose: Android’s recommended modern toolkit for building native UI
  - ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
  - Room: Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
  - [Hilt](https://dagger.dev/hilt/): for dependency injection.
  - CameraX: CameraX is a Jetpack library, built to help make camera app development easier.
- Architecture
  - MVI Architecture 
- [Material-Components](https://github.com/material-components/material-components-android): Material design components for building the UI.
- [Coil](https://coil-kt.github.io/coil/): An image loading library for Android backed by Kotlin Coroutines.
- [Zxing](https://github.com/zxing/zxing): is an open-source, multi-format 1D/2D barcode image processing library implemented in Java, with ports to other languages.

## Architecture
**Image Machine** is based on the MVI architecture and the Repository pattern.

![architecture](https://github.com/abdialidrus/image-machine/assets/20806662/56601362-313b-4fff-876e-67b9c56c7d1e)

The overall architecture of **Image Machine** is composed of two layers; the Business layer and the Data/Framework layer. Each layer has dedicated components and they have each different responsibilities. In **Image Machine** case we only have a single data source which is local/cache.
