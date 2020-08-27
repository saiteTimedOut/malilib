package fi.dy.masa.malilib.gui.widget.list.entry;

import fi.dy.masa.malilib.gui.widget.BaseTextFieldWidget;
import fi.dy.masa.malilib.gui.widget.LabelWidget;
import fi.dy.masa.malilib.gui.widget.button.GenericButton;
import fi.dy.masa.malilib.gui.widget.list.DataListWidget;

public class StringListEditEntryWidget extends BaseOrderableListEditEntryWidget<String>
{
    protected final String defaultValue;
    protected final String initialValue;
    protected final BaseTextFieldWidget textField;
    protected final GenericButton resetButton;

    public StringListEditEntryWidget(int x, int y, int width, int height, int listIndex, int originalListIndex,
                                     String initialValue, String defaultValue, DataListWidget<String> parent)
    {
        super(x, y, width, height, listIndex, originalListIndex, initialValue, parent);

        this.defaultValue = defaultValue;
        this.initialValue = initialValue;

        int textFieldWidth = width - 142;

        this.labelWidget = new LabelWidget(x + 2, y + 7, 32, 10, 0xC0C0C0C0, String.format("%5d:", originalListIndex + 1));
        this.textField = new BaseTextFieldWidget(x + 28, y + 2, textFieldWidth, 16, initialValue);

        this.resetButton = new GenericButton(x, y, -1, 16, "malilib.gui.button.reset.caps");
        this.resetButton.setRenderBackground(false);
        this.resetButton.setRenderOutline(true);
        this.resetButton.setOutlineColorNormal(0xFF404040);
        this.resetButton.setTextColorDisabled(0xFF505050);

        this.resetButton.setEnabled(initialValue.equals(this.defaultValue) == false);
        this.resetButton.setActionListener((btn, mbtn) -> {
            this.textField.setText(this.defaultValue);

            if (this.originalListIndex < this.dataList.size())
            {
                this.dataList.set(this.originalListIndex, this.defaultValue);
            }

            this.resetButton.setEnabled(this.textField.getText().equals(this.defaultValue) == false);
        });

        this.textField.setUpdateListenerAlways(true);
        this.textField.setListener((newText) -> {
            if (this.originalListIndex < this.dataList.size())
            {
                this.dataList.set(this.originalListIndex, newText);
            }

            this.resetButton.setEnabled(newText.equals(this.defaultValue) == false);
        });
    }

    @Override
    public void reAddSubWidgets()
    {
        super.reAddSubWidgets();

        this.addWidget(this.textField);
        this.addWidget(this.resetButton);
    }

    @Override
    public void focusWidget()
    {
        this.textField.setFocused(true);
    }

    @Override
    protected void updateSubWidgetsToGeometryChangesPre(int x, int y)
    {
        this.textField.setPosition(x, y + 2);
        this.nextWidgetX = this.textField.getRight() + 2;
    }

    @Override
    protected void updateSubWidgetsToGeometryChangesPost(int x, int y)
    {
        this.resetButton.setPosition(x, y + 2);
    }

    @Override
    protected String getNewDataEntry()
    {
        return "";
    }
}
