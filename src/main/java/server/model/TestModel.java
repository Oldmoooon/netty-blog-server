package server.model;

/**
 * @author guyue
 * @date 2018/9/13
 */
public class TestModel {
    public TestModel() {
    }

    public TestModel(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
