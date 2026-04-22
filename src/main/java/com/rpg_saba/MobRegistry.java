package com.rpg_saba.mob;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.model.ModelData;

public class MobRegistry {

    private static final Map<String, MobData> mobs = new HashMap<>();

    public static void init() {

        // ===== 通常モブ =====
        register(new MobData("ゾンビ兵", 7, 30, false));
        register(new MobData("スケルトン兵", 6, 25, false));
        register(new MobData("ドラウンド兵", 5, 25, false));
        register(new MobData("イカチャン", 6, 25, false));
        register(new MobData("ウィッチ兵", 6, 25, false));

        // ===== ボス =====
        register(new MobData("ボスゾンビ", 15, 200, true));
        register(new MobData("ボススケルトン", 12, 180, true));
        register(new MobData("ボスドラウンド", 17, 180, true));
    }

    private static void register(MobData data) {
        mobs.put(data.name, data);
    }

    public static MobData get(String name) {
        return mobs.get(name);
    }
}