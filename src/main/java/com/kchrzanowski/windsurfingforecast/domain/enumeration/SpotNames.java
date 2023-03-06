package com.kchrzanowski.windsurfingforecast.domain.enumeration;

public enum SpotNames {
    JASTARNIA("Jastarnia"),
    BRIDGETOWN("Bridgetown"),
    FORTALEZA("Fortaleza"),
    PISSOURI("Pissouri"),
    LE_MONRE("Le monre");
    private String name;

    SpotNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
