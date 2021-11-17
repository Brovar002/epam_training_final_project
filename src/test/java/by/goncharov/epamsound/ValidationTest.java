package by.goncharov.epamsound;

import by.goncharov.epamsound.service.Validator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidationTest {
    private static Validator validator;

    @BeforeClass
    public static void initValidator() {
        validator = new Validator();
    }

    @Test
    public void checkIsBonusValid() {
        String bonus = "120";
        boolean actual = validator.isBonusValid(bonus);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsTitleLengthValid() {
        String cash = "-1";
        boolean actual = validator.isCashValid(cash);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsEmailValidFirst() {
        String comment = "";
        boolean actual = validator.isCommentValid(comment);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkIsCommentValidSecond() {
        String comment = "11111111";
        boolean actual = validator.isCommentValid(comment);
        Assert.assertTrue(actual);
    }
}
