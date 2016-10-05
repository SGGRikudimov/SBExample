package uk.co.sgene.sbexample.crawlers.source1;

import lombok.AllArgsConstructor;
import lombok.Data;
import uk.co.sgene.sbexample.crawlers.MinedItem;

@Data
@AllArgsConstructor
public class Source1Item implements MinedItem {

    private String firstName;

    private String lastName;

    private Long id;
}
