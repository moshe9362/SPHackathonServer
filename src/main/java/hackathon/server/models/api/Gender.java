package hackathon.server.models.api;

public enum Gender {
    OTHER(0) ,MALE(1), FEMALE(2);

    private int value;

    Gender(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
