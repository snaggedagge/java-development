package dkarlsso.portal.aws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyTest {
    private String Namespace;
    private String OptionName;
    private String Value;
}
