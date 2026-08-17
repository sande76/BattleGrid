package com.sande76.battlegrid;

import com.sande76.battlegrid.model.GameBoard;
import com.sande76.battlegrid.model.Position;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

// Builds and displays the first BattleGrid screen.
public final class BattleGridApplication extends Application {

    private static final double CELL_SIZE = 76;

    @Override
    public void start(Stage stage) {
        // Create the starting game state.
        GameBoard board = new GameBoard();

        // Arrange the header, board, and footer.
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(24));
        root.setStyle("-fx-background-color: #101827;");
        root.setTop(createHeader());
        root.setCenter(createGrid(board));
        root.setBottom(createFooter(board));

        Scene scene = new Scene(root, 500, 580);
        stage.setTitle("BattleGrid");
        stage.setMinWidth(470);
        stage.setMinHeight(550);
        stage.setScene(scene);
        stage.show();
    }

    // Creates the title shown above the board.
    private VBox createHeader() {
        Label title = new Label("BATTLEGRID");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #f8fafc;");

        Label subtitle = new Label("Milestone 1 · 5×5 arena");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #94a3b8;");

        VBox header = new VBox(4, title, subtitle);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 22, 0));
        return header;
    }

    private GridPane createGrid(GameBoard board) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        // Add one visual cell for every board position.
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
        cell.setMinSize(CELL_SIZE, CELL_SIZE);

        // Alternate tile colors to make the grid easier to see.
        boolean alternate = (position.row() + position.column()) % 2 == 0;
        String background = alternate ? "#1e293b" : "#263449";
        cell.setStyle("-fx-background-color: " + background + ";"
                + "-fx-background-radius: 8px;"
                + "-fx-border-color: #475569;"
                + "-fx-border-radius: 8px;");

        // Draw the player marker when the robot occupies this cell.
        board.getRobotAt(position).ifPresent(robot -> {
            Circle marker = new Circle(25, Color.web("#38bdf8"));
            marker.setStroke(Color.web("#bae6fd"));
            marker.setStrokeWidth(3);

            Label initial = new Label("P");
            initial.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #082f49;");

            cell.getChildren().addAll(marker, initial);
            cell.setAccessibleText(robot.getName());
        });

        return cell;
    }

    // Shows information about the player robot below the board.
    private VBox createFooter(GameBoard board) {
        Position position = board.getPlayerRobot().getPosition();

        Label robotName = new Label(board.getPlayerRobot().getName());
        robotName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #38bdf8;");

        Label positionLabel = new Label(
                "Starting position: row " + (position.row() + 1) + ", column " + (position.column() + 1)
        );
        positionLabel.setStyle("-fx-text-fill: #cbd5e1;");

        Label nextStep = new Label("Next milestone: move the robot between cells");
        nextStep.setStyle("-fx-text-fill: #64748b;");

        VBox footer = new VBox(5, robotName, positionLabel, nextStep);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(22, 0, 0, 0));
        return footer;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
