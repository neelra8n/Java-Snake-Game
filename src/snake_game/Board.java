
package snake_game;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener{
        private Image apple;
        private Image head;
        private Image dot;
        
        private final int DOT_SIZE = 10;
        private final int All_DOTS = 900+10;
        private final int RANDOM_POSITION = 29;
        private final int x[] = new int[900];
        private final int y[] = new int[900];
        private int apple_x;
        private int apple_y;
        private int dots;
        
        private boolean leftDirection = false;
        private boolean rightDirection = true;
        private boolean upDirection = false;
        private boolean downDirection = false;
        private boolean inGame = true;
        private Timer timer;
        
    Board(){
        addKeyListener(new TAdapter());
        setPreferredSize(new Dimension(300, 300));
        setBackground(Color.BLACK);
        setFocusable(true);
        loadImages();
        initGames();
//        initGames();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this);
            for(int i = 0 ; i < dots; i++){
                if(i == 0){
                    g.drawImage(head, x[i], y[i], this);
                }
                else{
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }else{
            gameOver(g);
        }
    }
    
    public void gameOver(Graphics g){
        String str = "GAME OVER";
        
        Font font = new Font("SANS_SERIF", Font.BOLD, 14);
        FontMetrics metrices = getFontMetrics(font);
        
        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString(str, (300-metrices.stringWidth(str))/2, 300/2);
        
    }
    
    
    public void loadImages(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snake_game/icons/apple.png"));
        apple = i1.getImage();
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snake_game/icons/head.png"));
        head = i2.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snake_game/icons/dot.png"));
        dot = i3.getImage();
    }    
    
    public void initGames(){    
        dots = 3;
        
        for(int i = 0; i < dots; i++){
            x[i] = 50-i*DOT_SIZE;
            y[i] = 50;
        }
        
        locateApple();
        timer = new Timer(140, this);
        timer.start();
    }
    
    public void locateApple(){
        int r = (int)(Math.random())*RANDOM_POSITION;
        apple_x = (r*DOT_SIZE)+10;
        
        r = (int)(Math.random())*RANDOM_POSITION;
        apple_y = (r*DOT_SIZE)+10;
        
    }
    
    public void checkApple(){
        if(apple_x == x[0] && apple_y == y[0]){
             dots++;
             locateApple();
        }
    }
    
    public void checkCollision(){
        
        for(int i = dots; i > 0; i--){
            if((i > 4) && (x[0] == x[i]) && (y[0] == y[i])){
                inGame = false;
            }
        }
        
        if(x[0] >= 300 || x[0] <= 0 ){
            inGame = false;
        }
        
        
        if(y[0] >= 300 && y[0] <= 0){
            inGame = false;
        }
        
        
        
        if(!inGame) timer.stop();
    }
    
    public void move(){
        
        for(int i = dots; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        
        if(leftDirection){
            x[0] -= DOT_SIZE;
            
        }
        if(rightDirection){
            x[0] += DOT_SIZE;
        }
        if(upDirection){
            y[0] -= DOT_SIZE;
        }
        if(downDirection){
            y[0] += DOT_SIZE;
        }
        
    }
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent ae){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        
        repaint();
    }
    
    private class TAdapter extends KeyAdapter{
        
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            
            if(key == KeyEvent.VK_LEFT && !rightDirection){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if(key == KeyEvent.VK_RIGHT && !leftDirection){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if(key == KeyEvent.VK_UP && !downDirection){
                leftDirection = true;
                upDirection = true;
                rightDirection = false;
            }
            
            if(key == KeyEvent.VK_DOWN && !upDirection){
                rightDirection = false;
                downDirection = true;
                leftDirection = false; 
            }
        }
    }
    
}
