//package kr.co.mz.tutorial.jdbc.query;
//
//import kr.co.mz.tutorial.jdbc.db.HikariPoolFactory;
//import kr.co.mz.tutorial.jdbc.model.Board;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class SearchBoardAndBoardFile {
//    private static final String QUERY = """
//                SELECT
//                    *
//                FROM
//                    board b
//                        LEFT JOIN
//                    board_file bf ON b.seq = bf.board_seq
//            """;
//
//    public static void main(String[] args) throws SQLException, IOException {
//        var dataSource = HikariPoolFactory.createHikariDataSource();
//        try (var connection = dataSource.getConnection();
//             var preparedStatement = connection.prepareStatement(QUERY)) {
//            var resultSet = preparedStatement.executeQuery();
//            var boardMap = new LinkedHashMap<Integer, Board>(); //키,값 순서를 유지하고 저장
//            while (resultSet.next()) {
//                Board board = new Board();
//                board.setSeq(resultSet.getInt(1));
//                if (boardMap.containsValue(board)) {
//                    board = boardMap.get(resultSet.getInt(1));
//                }
//                if (resultSet.getInt(7) != 0) {
//                    var boardFile = new BoardFile();
//                    boardFile.setSeq(resultSet.getInt(7));
//                    boardFile.setBoardSeq(resultSet.getInt(8));
//                    boardFile.setFileUuid(resultSet.getString(9));
//                    boardFile.setFileName(resultSet.getString(10));
//                    board.addBoardFile(boardFile);
//                }
//                board.setTitle(resultSet.getString(2));
//                board.setContent(resultSet.getString(3));
//                board.setCustomerSeq(resultSet.getInt(4));
//                board.setCreatedTime(resultSet.getTimestamp(5));
//                board.setModifiedTime(resultSet.getTimestamp(6));
//                boardMap.put(resultSet.getInt(1), board);
//            }
//            for (Map.Entry<Integer, Board> entry : boardMap.entrySet()) {
//                System.out.println("board" + entry.getValue().getSeq());
//                for (BoardFile boardFile : entry.getValue().getBoardFileSet()) {
//                    System.out.println("file" + boardFile.getSeq());
//                }
//            }
//        }
//    }
//}