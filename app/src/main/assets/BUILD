package(default_visibility = ["//visibility:private"])

licenses(["notice"])  # Apache 2.0ke

exports_files(
    glob(
        ["**/*"],
        exclude = [
            "BUILD",
        ],
    ),
)

filegroup(
    name = "all_files",
    srcs = glob(
        ["**/*"],
        exclude = [
            "**/METADATA",
            "**/OWNERS",
        ],
    ),
    visibility = ["//tensorflow:__subpackages__"],
)
