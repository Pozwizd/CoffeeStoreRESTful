package spaceLab.entity;

import lombok.Getter;

@Getter
public enum Language {
    EN("English"),
    RU("Русский");

    Language(String language) {
        this.language = language;
    }

    private String language;

}