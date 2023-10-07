> `Bu projenin geliştirilmesi bırakılmıştır.`

# Gunluk Odul

Günlük ödül, oyunculara günlük ödülü teşvik amaçlı geliştirilmiş bir eklentidir. 7 Günlük giriş ödülü ayarlanabilir ve iki modu ile oyuncular ödüllendirilebilir.

> Casual: Oyuncunun arka arkaya girmesine gerek duymadan girdiği günden sonraki ödülü verir.
> Streak: Oyuncunun arka arkaya girmesini hedefler ve bir gün boşluk bırakırsa ödüllerde en başa döner.

> ` Ayrıca premium reward seçeneği ile VIP veya özel bir şeyi başarmış oyunculara farklı bir ödül verebilirsiniz. `

> Note: ` Auto Harvest module has not written atm. You can check my Farmer v6 Project to what is it like. `

## Config ve dil dosyası
<details>
  <summary>config.yml</summary>

    # "Streak" or "Casual" rewarding system
    Type: Streak
    debug: false
    forceOpen: true
    rewardSound: "LEVEL_UP"
    Rewards:
    first:
        material: STORAGE_MINECART
        tookedMaterial: MINECART
        firework:
            deploy: true
            amount: 3
        name: "&61. &eGünün Ödülü"
        lore:
        - ""
        - "&7Günlük bonusunuzu almak için"
        - "&7her gün tekrar gelin!"
        - ""
        - "&eÜst üste ne kadar çok gün katılırsanız,"
        - "&eödül o kadar iyi olur!"
        - "&7Durum: {status}"
        commands:
        - "msg %player% tebrikler!"
    second:
        material: STORAGE_MINECART
        tookedMaterial: MINECART
        firework:
            deploy: true
            amount: 3
        name: "&62. &eGünün Ödülü"
        lore:
        - ""
        - "&7Günlük bonusunuzu almak için"
        - "&7her gün tekrar gelin!"
        - ""
        - "&eÜst üste ne kadar çok gün katılırsanız,"
        - "&eödül o kadar iyi olur!"
        - "&7Durum: {status}"
        commands:
        - "msg %player% tebrikler!"
    third:
        material: STORAGE_MINECART
        tookedMaterial: MINECART
        firework:
            deploy: true
            amount: 3
        name: "&63. &eGünün Ödülü"
        lore:
        - ""
        - "&7Günlük bonusunuzu almak için"
        - "&7her gün tekrar gelin!"
        - ""
        - "&eÜst üste ne kadar çok gün katılırsanız,"
        - "&eödül o kadar iyi olur!"
        - "&7Durum: {status}"
        commands:
        - "msg %player% tebrikler!"
    fourth:
        material: STORAGE_MINECART
        tookedMaterial: MINECART
        firework:
            deploy: true
            amount: 3
        name: "&64. &eGünün Ödülü"
        lore:
        - ""
        - "&7Günlük bonusunuzu almak için"
        - "&7her gün tekrar gelin!"
        - ""
        - "&eÜst üste ne kadar çok gün katılırsanız,"
        - "&eödül o kadar iyi olur!"
        - "&7Durum: {status}"
        commands:
        - "msg %player% tebrikler!"
    fifth:
        material: STORAGE_MINECART
        tookedMaterial: MINECART
        firework:
            deploy: true
            amount: 3
        name: "&65. &eGünün Ödülü"
        lore:
        - ""
        - "&7Günlük bonusunuzu almak için"
        - "&7her gün tekrar gelin!"
        - ""
        - "&eÜst üste ne kadar çok gün katılırsanız,"
        - "&eödül o kadar iyi olur!"
        - "&7Durum: {status}"
        commands:
        - "msg %player% tebrikler!"
    sixth:
        material: STORAGE_MINECART
        tookedMaterial: MINECART
        firework:
            deploy: true
            amount: 3
        name: "&66. &eGünün Ödülü"
        requirement:
            permission: "gunlukodul.vip"
        reqErrorMessage: "&2GünlükÖdül &4VIP &cyetkin bulunmadığından ödül alamadın!"
        lore:
        - ""
        - "&7Günlük bonusunuzu almak için"
        - "&7her gün tekrar gelin!"
        - ""
        - "&eÜst üste ne kadar çok gün katılırsanız,"
        - "&eödül o kadar iyi olur!"
        - "&7Durum: {status}"
        commands:
        - "msg %player% tebrikler!"
    seventh:
        material: STORAGE_MINECART
        tookedMaterial: MINECART
        requirement:
            statistic_hours_played: 200
            reward:
            - "msg %player% 200 saat oynadığın için farklı bir ödül aldın!"
            - "give %player% diamond 1"
        firework:
            deploy: true
            amount: 3
        name: "&67. &eGünün Ödülü"
        lore:
        - ""
        - "&7Günlük bonusunuzu almak için"
        - "&7her gün tekrar gelin!"
        - ""
        - "&e200 Saat oynama ödülü:"
        - "&7Bir elmas"
        - ""
        - "&eÜst üste ne kadar çok gün katılırsanız,"
        - "&eödül o kadar iyi olur!"
        - "&7Durum: {status}"
        commands:
        - "msg %player% tebrikler!"
</details>

<details>
    <summary>lang.yml</summary>

    timeFormat:
    hour: "s"
    minute: "dk"
    second: "sn"

    Gui:
    name: "&2Günlük Ödül"
    rewardTooked: "&cAlınmış"
    rewardCannot: "&cAlınamaz"
    rewardATM: "&aBuradasın"
    rewardHelp:
        name: "&3Ödül Bilgisi"
        lore:
        - ""
        - " &8▪ &7Bu menü senin günlük ödülünü"
        - " &8▪ &7Alabileceğin yerdir. Durumunu buradan"
        - " &8▪ &7Inceleyip her gün yeni ödül alabilirsin!"
        
    fillItem:
    material: "STAINED_GLASS_PANE"
    damage: 7
        
    Titles:
    DailyReward: "&2Günlük Ödül"
    
    RewardMsg:
    alreadyTook: "&cÖdülü Alınamaz!"
    takingSuccess: "&aÖdül Alındı!"
    loginMsg: "&2GünlükÖdül &7Günlük ödülün hazır almayı unutma! &6/günlüködül"
    
    Placeholder:
    canTake: "&aAlınabilir"
</details>

## Kullanılan Kütüphaneler

* [spigot-api (1.16-R0.5-SNAPSHOT)](https://hub.spigotmc.org/stash/projects/SPIGOT/repos/spigot/browse)
* [json](https://www.json.org/json-en.html)
* [placeholderapi](https://www.spigotmc.org/resources/placeholderapi.6245/)

## Contributing

We welcome contributions from the community! If you would like to contribute, please follow these guidelines:

1. Fork the repository and clone it to your local machine.
2. Create a new branch for your feature or bug fix.
3. Make your changes, and ensure that your code is well-tested.
4. Create a pull request with a detailed description of your changes.

By contributing to this project, you agree to abide by the [Code of Conduct](CODE_OF_CONDUCT.md).