package conexiobasedatosuniversidad;

import java.sql.DriverManager;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class ConexionBasedeDatosUniversidad  {
    //Nombre y ruta de la base de datos
    static final String URL="jdbc:mysql://localhost:3306/universidad";
    //Usuario de la base de datos
    static final String USER = "root";
    
    public static void main(String[] args) throws SQLException {
    //Generar la conexion a la base de datos
        Connection conn=null;
        Statement statement = null;
        
        try{
            //Registrar el controlador(Driver)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //Establecer la comunicacion
            conn = DriverManager.getConnection(URL,USER, "");
                    
            if(conn!=null){
                JOptionPane.showMessageDialog(null,"Conectado a la base de datos universidad");
            
                // Crear la consulta SQL
                String sql = "SELECT id, nombre, apellido, genero, estatura, peso FROM profesor";
            
                // Crear una declaración (Statement)
                statement = conn.createStatement();
                
                //Ejecutar la consulta
                ResultSet resultset=statement.executeQuery(sql);
                
                //// Crear modelo de tabla por defecto (DefaultTableModel)
                DefaultTableModel defaultTableModel = new DefaultTableModel(
                        new String[]{"Id", "Nombre", "Apellido", "Genero", "Estatura", "Peso"}, 0
                );
                              // Procesar el resultado y agregar filas al modelo de tabla
                while (resultset.next()) {
                        int id = resultset.getInt("id");
                        String nombre = resultset.getString("nombre");
                        String apellido = resultset.getString("apellido");
                        String genero = resultset.getString("genero");
                        double estatura = resultset.getDouble("estatura");
                        double peso = resultset.getDouble("peso");
                        
                        defaultTableModel.addRow(new Object[]{id, nombre, apellido, genero, estatura, peso});
                }
                JTable table = new JTable(defaultTableModel);
                JScrollPane jScrollPane = new JScrollPane(table);
                JOptionPane.showMessageDialog(null,jScrollPane, "universidad", JOptionPane.INFORMATION_MESSAGE);
                } 
            
    }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Error de SQL="+ex.getMessage());
    }catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null,"Controlador JDBC no encontrado =" + ex.getMessage());
    }finally{
            try {
                if(conn!=null){
                    conn.close();
                    JOptionPane.showMessageDialog(null,"Conexion cerrada = ");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,"Error al cerrar la conexion="+e.getMessage());
            }
        }
    }
}
    


  