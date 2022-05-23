package com.liner.twod_exp.ringfantasy;

import com.liner.twod_exp.engine.core.ECore;
import com.liner.twod_exp.engine.core.InputListener;
import com.liner.twod_exp.engine.core.Renderer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("ForLoopReplaceableByForEach")
public class RingRender extends Renderer implements InputListener, Context {
    public static final int tileSize = 16;
    public static final int viewPortSize = 13;
    private final String[] mapNameList = {"MapS1", "MapT1", "MapT2", "MapT3", "MapP1", "MapW1", "MapW2", "MapN1", "MapN2", "MapD1", "MapD2", "MapD3", "MapD4"};
    private final List<Map> mapList;
    private String currentMapName = "MapS1";
    private Map currentMap;
    private Camera camera;
    private Player player;
    private Hud hud;
    private Event event;
    private List<Integer> inventory;
    private int inventorySize = 12;

    private int bartenderSpeech = 0;

    private Message message;
    private Question question;
    private Battle battle;
    private final Random random;



    public RingRender() {
        super(RingRender.class.getSimpleName());
        this.random = new Random();
        this.inventory = new ArrayList<>();
        mapList = new ArrayList<>();
        for (String mapName : mapNameList)
            mapList.add(new Map(mapName));
        loadMap("MapN1,6,24,40");
        // loadMap(Resource.eventList.get(0).data);
    }


    @Override
    public void render(ECore core) {
        if (Resource.reloadingResources)
            return;
        core.addListener(this);
        camera.draw(core, player);
        hud.draw(core);
        if (message != null)
            message.draw(core);
        if (question != null)
            question.draw(core);
        if (battle != null)
            battle.draw(core);


//        if (ingameEvent > 0) {
//            if (ingameEvent < 100) {
//                addCommand(okCommand);
//                if (ingameEvent != 1)
//                    addCommand(cancelCommand);
//                ingameEvent += 100;
//            }
//
//            Rectangle2D.Double messageBounds = new Rectangle2D.Double(8, tileSize * 13 + 16, tileSize * 13 - 12, core.getTextBounds(messageText).getHeight() + 8);
//            core.setColor(Color.WHITE);
//            core.drawString(messageText, messageBounds);
//            if (!commandList.isEmpty()) {
//                for (Command command : commandList) {
//                    String commandText = String.format("%s (%s)", command.getName(), command.getKeyCode());
//                    core.drawString(commandText, messageBounds.x, (messageBounds.y + messageBounds.height)+core.getTextBounds(commandText).getHeight());
//                }
//            }
//
////
////            core.setColor(Color.WHITE);
////            int startY = tileSize * 14;
////            for (String line : messageText.split("\\n")) {
////                core.drawString(line, tileSize, startY);
////                startY += core.getTextBounds(line).getHeight();
////            }
//        }
    }

    @Override
    public void tick(ECore eCore, long frameTime) {

    }

    private boolean playerCanMove(int x, int y) {
        int mapData = currentMap.getDataAt(x, y);
        int tileID = mapData % 100;
        int objectID = (mapData % 10000) / 100;
        int eventID = mapData / 10000;
        if (eventID != 0) {
            for (Event event : Resource.eventList) {
                if (event.id == eventID) {
                    this.event = event;
                    if (eventID == 59)
                        return false;
                    if (eventID == 97 || eventID == 100 || eventID == 105)
                        return true;
                }
            }
        }
        if (objectID == 0) {
            return tileID <= 18;
        } else return objectID <= 1 || objectID == 44;
    }

//
////    @Override
//    public void onExecuted(Command command) {
//        executedCommand = command;
//        if (command.equals(okCommand)) {
//            removeCommand(okCommand);
//            if (ingameEvent != 101)
//                removeCommand(cancelCommand);
//            if (ingameEvent == 102) {
//
//            }
//            if (ingameEvent == 103) {
//                ingameEvent = 0;
//                //TODO Shop menu
//                return;
//            }
//            if (ingameEvent == 104) {
//                player.key--;
//                map.setDataAt(map.getDataAt(player.x, player.y) % 10000, player.x, player.y);
//            }
//            if (ingameEvent == 105) {
//                ingameEvent = 0;
//                //TODO start battle
////                mapCanvas.addCommand(CommandMenu);
////                mapCanvas.addCommand(CommandSys);
////                bc.StartBattle(mapCanvas._fld011D, mapCanvas._fld011E, mapCanvas._fld011F, mapCanvas._fld0120);
////                if (mapCanvas._fld011D == 14) {
////                    TextFileInfo(bc, "King Lionel", "king1.txt");
////                } else {
////                    display.setCurrent(bc);
////                }
//            } else if (ingameEvent == 106) {
//                ingameEvent = 7;
//                messageText = "Do you like to accept this quest?";
//            } else if (ingameEvent == 107) {
//                map.setDataAt((map.getDataAt(player.x, player.y) % 10000) + 810000, player.x, player.y);
//                ingameEvent = 0;
//            } else if (ingameEvent == 108) {
//                map.setDataAt(960022, player.x, player.y);
//                ingameEvent = 0;
//            } else if (ingameEvent == 109) {
//                ingameEvent = 1;
//                switch (this.bartenderSpech) {
//                    case 0 -> messageText = "Bartender: \\n You need to EQUIP swords, armors and rings before use them.";
//                    case 1 -> messageText = "Bartender: \\n Use the treasure KEY carefully, because it just can be used once.";
//                    case 2 -> messageText = "Bartender: \\n Some monsters are very weak to FIRE or ICE spells, so cast the spells wisely.";
//                    case 3 -> messageText = "Bartender: \\n Check your STATUS from time to time, and make sure you are in the good condition.";
//                    case 4 -> messageText = "Bartender: \\n Use the RAINBOW OIL(s) will temporarily increase your attack power.";
//                    case 5 -> messageText = "Bartender: \\n Cast the SUMMON spell(s) will temporarily increase your spell power.";
//                    case 6 -> messageText = "Bartender: \\n There lots of HIDDEN treasures on this land, so find them carefully.";
//                    case 7 -> messageText = "Bartender: \\n Talk to the QUEEN. You may be the person she needs.";
//                }
//                this.bartenderSpech++;
//                if (this.bartenderSpech > 7) {
//                    this.bartenderSpech = 0;
//                }
//            } else {
//                ingameEvent = 0;
//            }
//
//        }
//        executedCommand = null;
//    }

    @Override
    public void keyPress(ECore core, int keyCode) {
        super.keyPress(core, keyCode);
        if (Resource.reloadingResources)
            return;
        if (keyCode == KeyEvent.VK_R) {
            Resource.reloadResources();
        }
        player.update();
        event = null;
        if (message != null && message.isShowing()) {
            message.keyEvent(keyCode);
        } else if (question != null && question.isShowing()) {
            question.keyEvent(keyCode);
        } else if (battle != null && battle.isRunning()) {
            battle.keyEvent(keyCode);
        } else {
            if (keyCode == KeyEvent.VK_UP && player.y > 0) {
                if (player.direction != 0) {
                    player.direction = 0;
                } else {
                    if (playerCanMove(player.x, player.y - 1)) {
                        player.y--;
                        if (random.nextFloat() > .9f)
                            player.hp = Math.min(player.maxHP, player.hp + 1);
                    }
                }
            } else if (keyCode == KeyEvent.VK_DOWN && player.y < currentMap.getMapHeight() - 1) {
                if (player.direction != 3) {
                    player.direction = 3;
                } else {
                    if (playerCanMove(player.x, player.y + 1)) {
                        player.y++;
                        if (random.nextFloat() > .9f)
                            player.hp = Math.min(player.maxHP, player.hp + 1);
                    }
                }
            } else if (keyCode == KeyEvent.VK_LEFT && player.x > 0) {
                if (player.direction != 1) {
                    player.direction = 1;
                } else {
                    if (playerCanMove(player.x - 1, player.y)) {
                        player.x--;
                        if (random.nextFloat() > .9f)
                            player.hp = Math.min(player.maxHP, player.hp + 1);
                    }
                }
            } else if (keyCode == KeyEvent.VK_RIGHT && player.x < currentMap.getMapWidth() - 1) {
                if (player.direction != 2) {
                    player.direction = 2;
                } else {
                    if (playerCanMove(player.x + 1, player.y)) {
                        player.x++;
                        if (random.nextFloat() > .9f)
                            player.hp = Math.min(player.maxHP, player.hp + 1);
                    }
                }
            }
            if (event != null) {
                switch (event.type) {
                    case 0:
                        loadMap(event.data);
                        break;
                    case 1:
                    case 16:
                        message = new Message(Resource.get(event.data, 1, '&'), 0, hud.getHeight());
                        message.show();
                        break;
                    case 2:
                        currentMap.setData(currentMap.getData(player) % 1000, player);
                        int coinsAmount = Resource.getInt(event.data, 1);
                        player.coins += coinsAmount;
                        message = new Message(String.format("Found: %s coins", coinsAmount), 0, hud.getHeight());
                        message.show();
                        break;
                    case 3:
                        boolean canPickup = inventory.size() < inventorySize;
                        if (canPickup) {
                            int itemData = Resource.getInt(event.data, 1);
                            int itemType = itemData / 100;
                            int itemIndex = itemData % 100;
                            String itemName = GetItemName(itemType, itemIndex);
                            if (itemType == 0 && itemIndex > 5) {
                                switch (itemIndex) {
                                    case 6 -> {
                                        itemName = itemName + " Strength +2";
                                        player.strengthMultiplier++;
                                    }
                                    case 7 -> {
                                        itemName = itemName + " Strength +4";
                                        player.attackMultiplier++;
                                    }
                                    case 8 -> {
                                        itemName = itemName + " HP +8";
                                        player.hpMultiplier++;
                                    }
                                    case 9 -> {
                                        itemName = itemName + " MP +8";
                                        player.mpMultiplier++;
                                    }
                                    case 10 -> {
                                        itemName = itemName + " Door key";
                                        player.keys++;
                                    }
                                }
                            } else {
                                inventory.add(itemData);
                            }
                            currentMap.setData(currentMap.getData(player) % 10000, player);
                            if (currentMap.getData(player) == 22)
                                currentMap.setData(23, player);
                            message = new Message(String.format("Found: %s", itemName), 0, hud.getHeight());
                            message.show();
                        } else {
                            message = new Message("Inventory is full, cant pick up items", 0, hud.getHeight());
                            message.show();
                        }
                        break;
                    case 4:
                        question = new Question("Restore HP/MP for 100 coins?", 0, hud.getHeight()) {
                            @Override
                            public void onSelected(int selection) {
                                if (selection == 0) {
                                    if (player.coins >= 100) {
                                        player.coins -= 100;
                                        player.y = 13;
                                        player.x = 4;
                                        player.direction = 0;
                                        player.hp = player.maxHP;
                                        player.mp = player.maxMP;
                                        question.hide();
                                    } else {
                                        question.hide();
                                        message = new Message("Sorry, you don't enough coins.", 0, hud.getHeight());
                                        message.show();
                                    }
                                }
                                question.hide();
                            }
                        };
                        question.show();
                        break;
                    case 5:
                        question = new Question("Open shop?", 0, hud.getHeight()) {
                            @Override
                            public void onSelected(int selection) {
                                question.hide();
                            }
                        };
                        question.show();
                        break;
                    case 6:
                        if (player.keys != 0) {
                            message = new Message("Door is locked, open it?", 0, hud.getHeight());
                            message.show();
                        } else {
                            message = new Message("Door is locked. No keys for open", 0, hud.getHeight());
                            message.show();
                        }
                        break;
                    case 7:
                        int monsterData = Resource.getInt(event.data, 1);
                        System.out.println(currentMap.getData(player) + "| "+monsterData);
                        int mapIndex = getMapIndex(currentMapName);
                        question = new Question(String.format("Fight with %s, HP:%s", Resource.monsters[monsterData].name, Resource.monsters[monsterData].hp), 0, hud.getHeight()) {
                            @Override
                            public void onSelected(int selection) {
                                if (selection == 0) {
                                    battle = new Battle(RingRender.this, monsterData);
                                    question.hide();
                                    battle.start();
                                } else
                                    question.hide();
                            }
                        };
                        question.show();
                        break;
                    case 8:
                        question = new Question("The queen seems very sad. Will you talk to her?", 0, hud.getHeight()) {
                            @Override
                            public void onSelected(int selection) {
                                if (selection == 0) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (String line : Resource.getText("queen.txt"))
                                        stringBuilder.append(line).append("\n");
                                    message = new Message(stringBuilder.toString(), 0, hud.getHeight());
                                    question.hide();
                                    message.show();
                                } else question.hide();
                            }
                        };
                        question.show();
                        break;
                    case 9:
                        question = new Question("The princess is locked with the chains. Release her?", 0, hud.getHeight()) {
                            @Override
                            public void onSelected(int selection) {
                                //queen.txt
                                question.hide();

                            }
                        };
                        question.show();
                        break;
                    case 11:
                        question = new Question("Have some drink and gossip?", 0, hud.getHeight()) {
                            @Override
                            public void onSelected(int selection) {
                                if (selection == 0) {
                                    String messageText = "";
                                    switch (bartenderSpeech) {
                                        case 0 -> messageText = "Bartender: \\n You need to EQUIP swords, armors and rings before use them.";
                                        case 1 -> messageText = "Bartender: \\n Use the treasure KEY carefully, because it just can be used once.";
                                        case 2 -> messageText = "Bartender: \\n Some monsters are very weak to FIRE or ICE spells, so cast the spells wisely.";
                                        case 3 -> messageText = "Bartender: \\n Check your STATUS from time to time, and make sure you are in the good condition.";
                                        case 4 -> messageText = "Bartender: \\n Use the RAINBOW OIL(s) will temporarily increase your attack power.";
                                        case 5 -> messageText = "Bartender: \\n Cast the SUMMON spell(s) will temporarily increase your spell power.";
                                        case 6 -> messageText = "Bartender: \\n There lots of HIDDEN treasures on this land, so find them carefully.";
                                        case 7 -> messageText = "Bartender: \\n Talk to the QUEEN. You may be the person she needs.";
                                    }
                                    bartenderSpeech++;
                                    if (bartenderSpeech > 7) {
                                        bartenderSpeech = 0;
                                    }
                                    message = new Message(messageText, 0, hud.getHeight());
                                    question.hide();
                                    message.show();
                                }
                                question.hide();
                            }
                        };
                        question.show();

                        break;
                    case 12:
                        //TODO End game
                        //messageText = "Game is end!";
                        break;
                    case 13:
                        currentMap.setData(971300, player);
                        currentMap.setDataAt(14, player.x - 1, player.y);
                        break;
                    case 14:
                        message = new Message("Where am I? The last thing I can remember is that phone rings ...", 0, hud.getHeight());
                        message.show();
                        break;
                    case 15:
                        message = new Message("I can not be stuck here. I must find my way home ...", 0, hud.getHeight());
                        message.show();
                        break;
                    case 17:
                        currentMap.setData(currentMap.getData(player) % 10000, player);
                        message = new Message(Resource.get(event.data, 1, '&'), 0, hud.getHeight());
                        message.show();
                        break;
                    default:
                        System.out.println(event);
                }
                System.out.println(event);
            }
        }
    }

    private Map getMap(String mapName) {
        for (Map map : mapList) {
            if (map.getMapName().equals(mapName))
                return map;
        }
        Map map = new Map(mapName);
        mapList.add(map);
        return map;
    }

    private void loadMap(String data) {
        this.currentMapName = Resource.getString(data, 1);
        currentMap = getMap(currentMapName);
        if (player == null) {
            player = new Player(0, 0, 0);
            hud = new Hud(player);
        }
        player.x = Resource.getInt(data, 2);
        player.y = Resource.getInt(data, 3);
        player.direction = Resource.getInt(data, 4) - 40;
        camera = new Camera(currentMap, player);
    }

    String[] potionList = {"", "Potion", "Magic Vial", "Rainbow Oil", "Full Potion", "Elixir", "Soul Stone", "Blood Stone", "Life Stone", "Magic Stone", "Key"};
    int[] potionCost = {0, 100, 200, 300, 500, 1000, 0, 0, 0, 0, 0};
    String[] weaponList = {"", "Short Sword", "Sword", "Sabre", "Board Sword", "Claymore", "Falchion", "Knight Sword", "Hero Sword", "Sun Sword"};
    int[] weaponCost = {0, 100, 500, 1000, 2000, 4000, 8000, 0, 0, 0};
    int[] weaponPower = {0, 2, 5, 8, 12, 16, 20, 24, 28, 32};
    String[] armorList = {"", "Leather Armor", "Quilted Armor", "Ring Mail", "Scale Mail", "Chain Mail", "Plate Mail", "Knight Armor", "Hero Armor", "Sun Armor"};
    int[] armorCost = {0, 100, 500, 1000, 2000, 4000, 8000, 0, 0, 0};
    int[] armorPower = {0, 2, 5, 8, 12, 16, 20, 24, 28, 32};
    String[] ringList = {"", "Shiva Ring", "Ifrit Ring", "Titan Ring", "Odin Ring", "Phoenix Ring"};
    String[] spellList = {"", "Ice Arrow", "Ice Lance", "Ice Storm", "Ice Meteor", "Fire Arrow", "Fire Ring", "Fire Blast", "Fire Meteor", "Summon Shiva", "Summon Infrit", "Summon Titan", "Summon Odin", "Summon Phoenix"};


    public String GetItemName(int type, int index) {
        return switch (type) {
            case 0 -> potionList[index];
            case 1 -> weaponList[index];
            case 2 -> armorList[index];
            default -> "";
        };
    }

    public int GetItemPrice(int type, int index) {
        return switch (type) {
            case 0 -> potionCost[index];
            case 1 -> weaponCost[index];
            case 2 -> armorCost[index];
            default -> 0;
        };
    }

    public int getMapIndex(String name) {
        for (int i = 0; i < mapNameList.length; i++) {
            if (mapNameList[i].equals(name))
                return i;
        }
        return -1;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Map getMap() {
        return currentMap;
    }

    @Override
    public Camera getCamera() {
        return camera;
    }

    @Override
    public Hud getHud() {
        return hud;
    }

    @Override
    public Event getEvent() {
        return event;
    }

    @Override
    public Message getMessage() {
        return message;
    }

    @Override
    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(Question question) {
        this.question = question;
    }
}
