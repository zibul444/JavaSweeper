import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.*;
import sweeper.Box;

public class JavaSweeper extends JFrame {
    private Game game;

    private final byte COLS = 32;
    private final byte ROWS = 32;
    private final byte BOMBS = 127;
    private final byte IMAGE_SIZE = 50;

    private JPanel panel;
    private JLabel label;

    public static void main(String[] args) {
        new JavaSweeper();
    }

    private JavaSweeper () {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initPanel();
        initLabel();
        initFrame();
    }

    private void initLabel() {
        label = new JLabel(getMessage());
        Font font = new Font("Tahoma", Font.BOLD, 18);
        label.setFont(font);
//        add (label, BorderLayout.NORTH);
        add (label, BorderLayout.SOUTH);
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords())
                    g.drawImage((Image) game.getBox(coord).image,
                                        coord.getX() * IMAGE_SIZE,
                                        coord.getY() * IMAGE_SIZE,
                            this);
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                byte x = (byte) (e.getX() / IMAGE_SIZE);
                byte y = (byte) (e.getY() / IMAGE_SIZE);
                Coord coord = new Coord(x, y);
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1 : game.pressLeftButton(coord); break;
                    case MouseEvent.BUTTON2 : game.start(); break;
                    case MouseEvent.BUTTON3 : game.pressRightButton(coord); break;
                }
                label.setText(getMessage());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(Ranges.getSize().getX() * IMAGE_SIZE,
                                            Ranges.getSize().getY() * IMAGE_SIZE));
        add (panel);
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Sweeper");
        setResizable(false);
        setLocationRelativeTo(null);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void setImages () {
        for (Box box : Box.values())
            box.image = getImage (box.name().toLowerCase());
        setIconImage(getImage("icon"));

    }

    private Image getImage (String name) {
        String fileName = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(fileName));
        return icon.getImage();
    }

    private String getMessage () {
        switch (game.getState()) {
            case BOMBED : return "Game over!";
            case WINNER : return "Congratulation! You Win!";
            case PLAYED :
            default     :   if (game.getTotalFlagged() == -1)
                                return "Welcom to Sweeper!";
                            else
                                return "Flagged " +
                                        game.getTotalFlagged() + " of " +
                                        game.getTotalBombs() + " bombs.";
        }
    }
}
