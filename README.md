# Cucumber Library [![](http://cf.way2muchnoise.eu/full_272335_downloads.svg)](https://minecraft.curseforge.com/projects/cucumber)
A library of shared code and functionality used by my mods.

[Documentation](https://blakesmods.com/docs/cucumber)

## Download

The official release builds can be downloaded from the following websites.

- [Blake's Mods](https://blakesmods.com/cucumber/download)
- [CurseForge](https://www.curseforge.com/minecraft/mc-mods/cucumber)

## Development

To use this mod in a development environment, you will need to add the following to your `build.gradle`.

```groovy
repositories {
    maven {
        url 'https://maven.blakesmods.com'
    }
}

dependencies {
    implementation fg.deobf('com.blakebr0.cucumber:Cucumber:<minecraft_version>-<mod_version>')
}
```

## License

[MIT License](./LICENSE)
