# 🧱 KUtils

A lightweight utility library for Bukkit/Spigot plugin development.  
Provides fluent item creation, advanced chat formatting (including hex colors), and time utilities — all optimized for performance and readability.

![img](https://img.shields.io/github/stars/kaepsis/kutils)
![img](https://img.shields.io/github/v/release/kaepsis/kutils)
![img](https://img.shields.io/github/downloads/kaepsis/kutils/total)

---

## ✨ Features

- **ItemFactory** – Chainable API to build customized `ItemStack` objects easily.
- **Chat** – Advanced color formatting with support for `&` codes and hex (`&#RRGGBB`) colors.
- **Time** – Format durations and convert Minecraft-style strings like `10m`, `2h`, `1d` into real-world timestamps.
- Singleton-based utility classes.
- 100% Bukkit/Spigot API compatible.

---

## 📦 Installation

Clone or copy the classes directly into your plugin project.

Add the repository and dependency. Replace **{VERSION}** with the version shown in the badge.

### Gradle
```gradle
repositories {
    maven { url = "https://jitpack.io" }
}

dependencies {
    implementation "com.gituhb.kaepsis:kutils:{VERSION}
}
```

### Maven
```xml
<repository>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.kaepsis</groupId>
    <artifactId>kutils</artifactId>
    <version>{VERSION}</version>
</dependency>
```

---

## 📚 API Overview

You can read Javadocs [here](<https://kaepsis.github.io/kutils/>)

### `ItemFactory`

```java
ItemStack sword = new ItemFactory(new ItemStack(Material.DIAMOND_SWORD))
    .name("&bEpic Sword")
    .unbreakable(true)
    .enchant(Enchantment.DAMAGE_ALL, 5, true)
    .lore(List.of("&7Kills monsters", "&7Sharp as heck"))
    .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
    .build();
```

### `Chat`

```java
Chat.getInstance().send(player, "&aWelcome &b{name}&a!", "%name%", player.getName());

String colored = Chat.getInstance().color("&bHello &#FF00FFworld");
String stripped = Chat.getInstance().removeColors(colored);
```

### `Time`

```java
String formatted = Time.getInstance().strftime(7265000L); // "2h 1m 5s"

long futureMillis = Time.getInstance().minecraftTimeToInstant(Instant.now(), "5m");

String readableDate = Time.getInstance().toFormattedDate(futureMillis);
```

---

## 🧪 Dependencies

- Java 17+
- Bukkit/Spigot or Paper API

---

## 🤝 Contributing

Pull requests, suggestions and improvements are welcome!

1. Fork the repository
2. Create a feature branch
3. Commit and push
4. Open a PR 🚀