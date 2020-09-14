package pl.trollcraft.creative.games;

public enum Result {

    JOINED ("Dolaczono do gry!"),
    LEFT ("Opuszczono gre."),

    ALREADY_JOINED ("Bierzesz juz udzial w tej grze."),
    NOT_IN_GAME ("Nie bierzesz udzialu w tej grze."),

    NO_START_POINT ("Brak punktu startowego."),
    NO_FALLBLOCK ("Brak bloku spadkowego."),
    NO_ENDBLOCK ("Brak bloku koncowego"),

    OK ("OK");

    private String message;

    Result(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
