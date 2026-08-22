package com.sande76.battlegrid;

import com.sande76.battlegrid.model.GameBoard;
import com.sande76.battlegrid.model.Position;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

// Builds and displays the BattleGrid screen.
public final class BattleGridApplication extends Application {

    private static final double CELL_SIZE = 60;

    private BorderPane root;

    @Override
    public void start(Stage stage) {
        GameBoard board = new GameBoard();

        // Place the title, grid, and status text on the screen.
        root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setTop(createTitle());
        root.setCenter(createGrid(board));
        root.setBottom(createStatus(board));

        Scene scene = new Scene(root, 380, 430);
        stage.setTitle("BattleGrid");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private Label createTitle() {
        Label title = new Label("BattleGrid");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(0, 0, 20, 0));
        return title;
    }

    private GridPane createGrid(GameBoard board) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        // Add one cell for every board position.
        for (int row = 0; row < board.getSize(); row++) {
            for (int column = 0; column < board.getSize(); column++) {
                Position position = new Position(row, column);
                grid.add(createCell(board, position), column, row);
            }
        }

        return grid;
    }

    private StackPane createCell(GameBoard board, Position position) {
        StackPane cell = new StackPane();
        cell.setPrefSize(CELL_SIZE, CELL_SIZE);
        cell.setStyle("-fx-background-color: white; -fx-border-color: black;");

        // Mark the cell occupied by the player robot.
        board.getRobotAt(position).ifPresent(robot -> {
            boolean isPlayer = robot == board.getPlayerRobot();

            Circle marker = new Circle(20);
            marker.setFill(isPlayer ? Color.CORNFLOWERBLUE : Color.LIGHTCORAL);
            marker.setStroke(Color.BLACK);

            Label initial = new Label(isPlayer ? "P" : "E");
            initial.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            cell.getChildren().addAll(marker, initial);
            cell.setAccessibleText(robot.getName());
        });

        cell.setOnMouseClicked(event -> handleCellClick(board, position));
        return cell;
    }

    private void handleCellClick(GameBoard board, Position position) {
        boolean actionCompleted;

        if (board.getEnemyRobot().getPosition().equals(position)) {
            actionCompleted = board.attackEnemy();
        } else {
            actionCompleted = board.movePlayer(position);
        }

        if (actionCompleted) {
            refreshBoard(board);
        }
    }

    private void refreshBoard(GameBoard board) {
        root.setCenter(createGrid(board));
        root.setBottom(createStatus(board));
    }

    private VBox createStatus(GameBoard board) {
        String message;

        if (board.hasPlayerWon()) {
            message = "Enemy defeated - You win!";
        } else if (board.hasPlayerLost()) {
            message = "You lose!";
        } else {
            message = "Player Health: " + board.getPlayerRobot().getHealth()
                    + "    Enemy Health: " + board.getEnemyRobot().getHealth()
                    + "\nMove next to the enemy, then click it to attack.";
        }

        Label status = new Label(message);
        status.setAlignment(Pos.CENTER);

        Button restartButton = new Button("Restart Game");
        restartButton.setOnAction(event -> refreshBoard(new GameBoard()));

        VBox statusArea = new VBox(10, status, restartButton);
        statusArea.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(statusArea, Pos.CENTER);
        BorderPane.setMargin(statusArea, new Insets(20, 0, 0, 0));
        return statusArea;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
