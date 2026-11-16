package task3;

public class BorderDecorator implements ViewElement {
    private final ViewElement wrappedView;
    private static final int PADDING = 10;
    private static final int BORDER_WIDTH = 3;

    public BorderDecorator(ViewElement wrappedView) {
        this.wrappedView = wrappedView;
    }

    @Override
    public int getWidth() {
        return wrappedView.getWidth() + PADDING * 2;
    }

    @Override
    public int getHeight() {
        return wrappedView.getHeight() + PADDING * 2;
    }

    @Override
    public String getFileName() {
        return wrappedView.getFileName();
    }

    @Override
    public String createSvg(Model model) {
        String svg = wrappedView.createSvg(model);

        return String.format("""
                <svg x="%d" y="%d" width="%d" height="%d">
                %s
                </svg>
                <rect x="%d" y="%d" width="%d" height="%d" fill="none" stroke="#3366CC" stroke-width="%d"/>
                """,
                PADDING, PADDING, wrappedView.getWidth(), wrappedView.getHeight(), svg,
                0, 0, getWidth(), getHeight(), BORDER_WIDTH);
    }

}
