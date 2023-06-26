// NOTE: THIS IS AN AUTO-GENERATED FILE. IT HAD BEEN CREATED USING TEAMCITY.DOCKER PROJECT. ...
// ... IF NEEDED, PLEASE, EDIT DSL GENERATOR RATHER THAN THE FILES DIRECTLY. ... 
// ... FOR MORE DETAILS, PLEASE, REFER TO DOCUMENTATION WITHIN THE REPOSITORY.
package generated

import generated.inactive.PushLocalLinux1804
import generated.inactive.PushLocalWindows1803
import generated.inactive.PushLocalWindows1903
import generated.inactive.PushLocalWindows1909
import generated.staging.PublishLocal
import generated.staging.PushLocalWindows2004
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object LocalProject : Project({
    name = "Staging registry"
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
