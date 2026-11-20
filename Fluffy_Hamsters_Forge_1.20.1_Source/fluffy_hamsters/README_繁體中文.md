# Fluffy Hamsters — Minecraft 1.20.1 Forge

這是 **可編譯的 Forge 模組原始碼專案**，不是已編譯的 JAR。

## 已實作（原始碼）
- 長毛黃金鼠實體與生成蛋（創造模式「生成蛋」分類）
- 奶油金、白色毛色的方塊模型及 PNG 材質（依參考圖設計的簡化第一版，非逐像素還原）
- 行走腿部動畫、閒逛、注視玩家、跟隨主人
- 用小麥種子、南瓜種子或西瓜種子馴服（約 1/3 機率）；馴服後空手右鍵切換坐下／跟隨
- 種子可回復生命值

## 編譯方式
1. 安裝 **JDK 17**。
2. 安裝 **Gradle 8.x**（建議 8.8；或使用官方 Forge 1.20.1 MDK 的 Gradle Wrapper）。
3. 在專案根目錄執行 `gradle build`；第一次編譯需要網路下載 Forge 依賴。
4. 成功後將 `build/libs/fluffy_hamsters-0.1.0.jar` 放入 Minecraft 1.20.1 Forge 的 `mods` 資料夾。
5. 遊戲內使用 `/summon fluffyhamsters:hamster` 或從創造模式取得生成蛋。

## 注意
此環境無法連線取得 Forge 的編譯依賴，故 **沒有完成編譯與遊戲實測**。可能需要依 Forge 47.3.0 API 調整程式碼。不能直接把本 ZIP 放進 mods 資料夾。
尚未實作：精細長毛幾何、縮成球睡覺、捧食、站立、跑輪、抱起、自然生成、多毛色。
