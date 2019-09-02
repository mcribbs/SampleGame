package net.mcribbs;

import net.mcribbs.engine.GameContainer;
import net.mcribbs.engine.GameEngine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

class SampleGame extends GameContainer {

   private SampleGame() {
      super();
   }

   private int gameMode = 0;
   private static final int NUM_MODES = 4;

   // Mode 2 variables
   private float numEntities = 20;
   private float radius = 5f;
   private boolean filled = false;

   // Mode 3 variables
   private float pX;
   private float pY;
   private float speed = 0.05f;

   // Mode 4 variables
   private boolean first = true;
   private int lastX;
   private int lastY;

   public static void main(String[] args) {
      System.out.println("Starting game...");
      SampleGame myGame = new SampleGame();
      GameEngine engine = new GameEngine(myGame);
      engine.start();
   }

   @Override
   protected void onStartup() {
      title = "Matt's Test Game";
      width = 320;
      height = 240;
      scale = 3f;

      pX = width / 2;
      pY = height / 2;
   }

   @Override
   protected void onFrameUpdate(float elapsedTime) {

      if (gameMode == 0) {
         window.renderer.clear();
         Random r = new Random();
         for (int i = 0; i < 2000; i++) {
            int x = Math.abs(r.nextInt()) % width;
            int y = Math.abs(r.nextInt()) % height;
            Color c = new Color(Math.abs(r.nextInt()) % 255, Math.abs(r.nextInt()) % 255, Math.abs(r.nextInt()) % 255);

            window.renderer.draw(x, y, c);
         }
      } else if (gameMode == 1) {
         window.renderer.clear();
         Random r = new Random();
         for (int i = 0; i < numEntities; i++) {
            int x = Math.abs(r.nextInt()) % width;
            int y = Math.abs(r.nextInt()) % height;
            Color c = new Color(Math.abs(r.nextInt()) % 255, Math.abs(r.nextInt()) % 255, Math.abs(r.nextInt()) % 255);

            if (filled) {
               window.renderer.fillCircle(x, y, (int)radius, c);
            } else {
               window.renderer.drawCircle(x, y, (int)radius, c);
            }
         }

         if (input.isKeyHeld(KeyEvent.VK_RIGHT)) {
            radius = radius + 0.005f;
            if (radius > 100) radius = 100;
         }
         if (input.isKeyHeld(KeyEvent.VK_LEFT)) {
            radius = radius - 0.005f;
            if (radius < 0) radius = 0;
         }
         if (input.isKeyHeld(KeyEvent.VK_UP)) {
            numEntities = numEntities + 0.05f;
            if (numEntities > 100) numEntities = 100;
         }
         if (input.isKeyHeld(KeyEvent.VK_DOWN)) {
            numEntities = numEntities - 0.05f;
            if (numEntities < 0) numEntities = 0;
         }
         if (input.isKeyReleased(KeyEvent.VK_F)) {
            filled = !filled;
         }
         if (input.isKeyReleased(KeyEvent.VK_R)) {
            radius = 5f;
            numEntities = 20;
         }
      } else if (gameMode == 2) {
         window.renderer.clear();
         int top = (int)(pX - 1);
         int left = (int)(pY - 1f);
         window.renderer.fillRect(top, left, 3, 3, Color.CYAN);

         if (input.isKeyHeld(KeyEvent.VK_RIGHT) || input.isKeyHeld(KeyEvent.VK_D)) {
            pX = pX + speed * elapsedTime;
         }
         if (input.isKeyHeld(KeyEvent.VK_LEFT) || input.isKeyHeld(KeyEvent.VK_A)) {
            pX = pX - speed * elapsedTime;
         }
         if (input.isKeyHeld(KeyEvent.VK_UP) || input.isKeyHeld(KeyEvent.VK_W)) {
            pY = pY - speed * elapsedTime;
         }
         if (input.isKeyHeld(KeyEvent.VK_DOWN) || input.isKeyHeld(KeyEvent.VK_S)) {
            pY = pY + speed * elapsedTime;
         }

         if (pX > width) pX = 0;
         if (pX < 0) pX = width;
         if (pY > height) pY = 0;
         if (pY < 0) pY = height;
      } else if (gameMode == 3) {
         if (first) {
            window.renderer.clear();
            first = false;
            if (input.isButtonHeld(1)) {
               window.renderer.draw((int)input.mouseX, (int)input.mouseY);
            }
         } else {
            if (input.isButtonHeld(1)) {
               window.renderer.drawLine(lastX, lastY, (int) input.mouseX, (int) input.mouseY);
            }
         }
         lastX = (int)input.mouseX;
         lastY = (int)input.mouseY;

         if (input.isKeyReleased(KeyEvent.VK_C)) {
            window.renderer.clear();
         }
      }

      if (input.isKeyReleased(KeyEvent.VK_SPACE)) {
         gameMode++;
         first = true;

         if (gameMode > NUM_MODES - 1) {
            gameMode = 0;
         }
      }
   }

   @Override
   protected void onShutdown() {
      System.out.println("Quitting");
      System.exit(0);
   }
}
