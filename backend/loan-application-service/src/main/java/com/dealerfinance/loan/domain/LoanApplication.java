@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class LoanApplication {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Dealer dealer;

    private String vehicleType; // e.g., "Truck", "Trailer"
    private String buyerName;
    private BigDecimal amount;
    private Integer termMonths;
    private String status; // DRAFT, SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED, FUNDED

    @OneToMany(mappedBy = "loanApplication", cascade = CascadeType.ALL)
    private List<ApplicationStatusHistory> statusHistory = new ArrayList<>();

    @OneToMany(mappedBy = "loanApplication", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<>();
}