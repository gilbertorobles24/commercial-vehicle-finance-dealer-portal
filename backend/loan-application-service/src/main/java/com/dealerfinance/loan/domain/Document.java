@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Document {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private LoanApplication loanApplication;

    private String fileName;
    private String type; // e.g., "Proof of Income"
}