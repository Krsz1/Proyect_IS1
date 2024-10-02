package backend.proyect_doctic_is1.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Categories")

public class CategoriesModel {

    @Id
    private String id;
    private String name;
    private String subCategory;  
}
