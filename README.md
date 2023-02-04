# Cucumber Library

<p align="left">
    <a href="https://blakesmods.com/cucumber" alt="Downloads">
        <img src="https://img.shields.io/endpoint?url=https://api.blakesmods.com/v2/badges/cucumber/downloads&style=for-the-badge" />
    </a>
    <a href="https://blakesmods.com/cucumber" alt="Latest Version">
        <img src="https://img.shields.io/endpoint?url=https://api.blakesmods.com/v2/badges/cucumber/version&style=for-the-badge" />
    </a>
    <a href="https://blakesmods.com/cucumber" alt="Minecraft Version">
        <img src="https://img.shields.io/endpoint?url=https://api.blakesmods.com/v2/badges/cucumber/mc_version&style=for-the-badge" />
    </a>
    <a href="https://blakesmods.com/docs/cucumber" alt="Docs">
        <img src="https://img.shields.io/static/v1?label=docs&message=view&color=brightgreen&style=for-the-badge" />
    </a>
    <a href="https://blakesmods.com/wiki/cucumber" alt="Wiki">
        <img src="https://img.shields.io/static/v1?label=wiki&message=view&color=brightgreen&style=for-the-badge" />
    </a>
</p>

A library of shared code and functionality used by my mods.

## Download

The official release builds can be downloaded from the following websites.

- [Blake's Mods](https://blakesmods.com/cucumber/download)
- [CurseForge](https://www.curseforge.com/minecraft/mc-mods/cucumber)
- [Modrinth](https://modrinth.com/mod/cucumber)

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
