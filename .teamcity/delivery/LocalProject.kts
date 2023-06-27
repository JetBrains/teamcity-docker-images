package delivery

import delivery.inactive.PushLocalLinux1804
import delivery.inactive.PushLocalWindows1803
import delivery.inactive.PushLocalWindows1903
import delivery.inactive.PushLocalWindows1909
import delivery.staging.PushLocalLinux2004
import delivery.staging.PushLocalWindows1809
import delivery.staging.PushLocalWindows2004
import delivery.staging.manifest.PublishLocal
import delivery.arm.PushStagingLinux2004_Aarch64
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object LocalProject : Project({
    name = "TeamCity Docker Images Deployment into Staging Registry"
    description = "Configurations designed for the build of TeamCity Docker Images and their publishing into staging registry."

    buildType(PushLocalLinux1804.push_local_linux_18_04)
    buildType(PushLocalLinux2004.push_local_linux_20_04)
    buildType(PushLocalWindows1803.push_local_windows_1803)
    buildType(PushLocalWindows1809.push_local_windows_1809)
    buildType(PushLocalWindows1903.push_local_windows_1903)
    buildType(PushLocalWindows1909.push_local_windows_1909)
    buildType(PushLocalWindows2004.push_local_windows_2004)
    buildType(PublishLocal.publish_local)
    buildType(ImageValidation.image_validation)

    // aarch64 (ARM) images
    buildType(PushStagingLinux2004_Aarch64.push_staging_linux_2004_aarch64)
})
