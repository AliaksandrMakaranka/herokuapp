package com.herokuapp.internet;

public enum DragDropEnum {
    COPENHAGEN,
    DENMARK,
    ITALY,
    MADRID,
    NORWAY,
    OSLO,
    ROME,
    SEOUL,
    SOUTHKOREA,
    SPAIN,
    STOCKHOLM,
    SWEDEN,
    UNITEDSTATES,
    WASHINGTON;
    private String xpath;

    DragDropEnum(String xpath) {
        this.xpath = xpath;
    }

    DragDropEnum() {
    }

    public String getXpath() {
        return this.xpath;
    }

//TODO
    public  DragDropEnum DragDropEnum(String xpath) {
        return switch (xpath) {
            case "//div[@id=\"box1\"]" -> OSLO;
            case "//div[@id=\"box101\"]" -> NORWAY;

            case "//div[@id=\"box2\"]" -> STOCKHOLM;
            case "//div[@id=\"box102\"]" -> SWEDEN;

            case "//div[@id=\"box4\"]" -> WASHINGTON;
            case "//div[@id=\"box103\"]" -> UNITEDSTATES;

            case "//div[@id=\"box5\"]" -> COPENHAGEN;
            case "//div[@id=\"box104\"]" -> DENMARK;

            case "//div[@id=\"box6\"]" -> SEOUL;
            case "//div[@id=\"box105\"]" -> SOUTHKOREA;

            case "//div[@id=\"box7\"]" -> ROME;
            case "//div[@id=\"box106\"]" -> ITALY;

            case "//div[@id=\"box8\"]" -> MADRID;
            case "//div[@id=\"box107\"]" -> SPAIN;
            default -> throw new NullPointerException();
        };
    }
}
