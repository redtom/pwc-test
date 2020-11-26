package haywood.tom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.CompareToBuilder;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Contact implements Comparable<Contact> {

    @EqualsAndHashCode.Include
    private String name;
    private String phoneNumber;

    /**
     * Implement compareTo so sorting is based on the name field.
     * If multiple sort orders were possible, then I would move
     * this out of the class in to a Comparator object.
     */
    @Override
    public int compareTo(Contact other) {
        if (other != null) {
            return new CompareToBuilder()
                .append(getName(), other.getName())
                .toComparison();
        } else {
            return -1;
        }
    }
}
