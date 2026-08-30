package pl.quiz.webApplication.enums;

/**
 * This enum contains every available type of question
 * <p>
 * Created on 02.08.2026
 * @author Maciej
 * @version 0.1
 */
public enum Type {
    /**
     * This means question only has four possible answers: A/B/C/D more than one can be correct
     */
    ABCD,

    /**
     * This means question is about date, it requires format DD/MM/YYYY
     */
    DATE,

    /**
     * This means question is closed and only accepts two answers: True and False
     */
    TF,

    /**
     * This means this question is of open type and an answer can be anything
     */
    OPEN,

    /**
     * This means question is closed and only accepts two answers: Yes and No
     */
    YN
}
