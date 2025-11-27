package io.github.kei_1111.newsflow.android.core.ui.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Phone - Light - Standard Font",
    device = Devices.PHONE,
)
@Preview(
    name = "Phone - Light - Large Font",
    device = Devices.PHONE,
    fontScale = 1.5f,
)
@Preview(
    name = "Phone - Dark - Standard Font",
    device = Devices.PHONE,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Phone - Dark - Large Font",
    device = Devices.PHONE,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    fontScale = 1.5f,
)
@Preview(
    name = "Tablet - Light - Standard Font",
    device = Devices.TABLET,
)
@Preview(
    name = "Tablet - Light - Large Font",
    device = Devices.TABLET,
    fontScale = 1.5f,
)
@Preview(
    name = "Tablet - Dark - Standard Font",
    device = Devices.TABLET,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Tablet - Dark - Large Font",
    device = Devices.TABLET,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    fontScale = 1.5f,
)
annotation class ScreenPreviews
