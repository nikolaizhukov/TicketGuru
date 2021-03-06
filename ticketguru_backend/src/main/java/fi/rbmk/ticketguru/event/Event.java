package fi.rbmk.ticketguru.event;

import fi.rbmk.ticketguru.ageLimit.AgeLimit;
import fi.rbmk.ticketguru.venue.Venue;
import fi.rbmk.ticketguru.eventOrganizer.EventOrganizer;
import fi.rbmk.ticketguru.eventTicket.EventTicket;
import fi.rbmk.ticketguru.eventType.EventType;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Table(name = "Events")
public class Event extends ResourceSupport {

    @Id // Määritellään kenttä ID:ksi
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automaattinen juoksenva numerointi. HUOM! Käytetään
                                                        // GenerationType.IDENTITY spring bootin bugin vuoksi
    @Column(name = "event_ID") // Tietokannassa olevan kentän nimi
    private Long event_ID; // Muuttujan nimi, ei välttämättä sama kuin tietokannassa. Tässä tapauksessa id
                           // jotta automaattisesti generoidut funktiot toimivat

    @NotEmpty(message = "Event name is required") // Lisätään pakollisiin kenttiin virheilmoituksen kera
    @Length(max = 250) // Määritellään kaikille kentille jotka sen vaativat maksimipituus
    @Column(name = "name")
    private String name;

    @NotNull(message = "Event type is required")
    @ManyToOne // Relaatio
    @JoinColumn(name = "eventType_ID") // Mitä kenttää tietokannassa viitataan
    private EventType eventType; // Huomatkaa että FK tyyppiset kentät ovat objektityyppi, ei string, long tai
                                 // int

    @NotNull(message = "Event datetime is required")
    @Column(name = "dateTime")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateTime; // Aikaleimoihin joissa vaaditaan sekä päivä että kellonaika käytetään
                                    // LocalDateTime tyyppiä

    @NotNull(message = "Event organizer is required")
    @ManyToOne
    @JoinColumn(name = "eventOrganizer_ID")
    private EventOrganizer eventOrganizer;

    @NotNull(message = "Event venue is required")
    @ManyToOne
    @JoinColumn(name = "venue_ID")
    private Venue venue;

    @NotNull(message = "Event ticket capacity is required")
    @Column(name = "ticketCapacity")
    private Long ticketCapacity;

    @NotNull(message = "Age limit must be set")
    @ManyToOne
    @JoinColumn(name = "ageLimit_ID")
    private AgeLimit ageLimit;

    @Length(max = 500)
    @Column(name = "info")
    private String info;

    @OneToMany(mappedBy = "event")
    private List<EventTicket> eventTickets;

    // Tyhjä construktori
    public Event() {
    }

    // Kopioidaan olemassaolevasta/ syötetään olemassa oleva constructorille
    public Event(Event event) {
    }

    // Constuctori vain pakollisille kentille
    public Event(String name, EventType eventType, LocalDateTime dateTime, EventOrganizer eventOrganizer, Venue venue,
            Long ticketCapacity, AgeLimit ageLimit) {
        this.name = name;
        this.eventType = eventType;
        this.dateTime = dateTime;
        this.eventOrganizer = eventOrganizer;
        this.venue = venue;
        this.ticketCapacity = ticketCapacity;
        this.ageLimit = ageLimit;
    }

    // Constructori missä mukana vapaaehtoiset kentät
    public Event(String name, EventType eventType, LocalDateTime dateTime, EventOrganizer eventOrganizer, Venue venue,
            Long ticketCapacity, AgeLimit ageLimit, String info) {
        this.name = name;
        this.eventType = eventType;
        this.dateTime = dateTime;
        this.eventOrganizer = eventOrganizer;
        this.venue = venue;
        this.ticketCapacity = ticketCapacity;
        this.ageLimit = ageLimit;
        this.info = info;
    }

    // Getterit
    public Long getEvent_ID() {
        return this.event_ID;
    }

    public String getName() {
        return this.name;
    }

    public EventType getEventType() {
        return this.eventType;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public EventOrganizer getEventOrganizer() {
        return this.eventOrganizer;
    }

    public Venue getVenue() {
        return this.venue;
    }

    public Long getTicketCapacity() {
        return this.ticketCapacity;
    }

    public AgeLimit getAgeLimit() {
        return this.ageLimit;
    }

    public String getInfo() {
        return this.info;
    }

    public List<EventTicket> getEventTickets() {
        return this.eventTickets;
    }

    // Setterit
    public void setName(String name) {
        this.name = name;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setEventOrganizer(EventOrganizer eventOrganizer) {
        this.eventOrganizer = eventOrganizer;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public void setTicketCapacity(Long ticketCapacity) {
        this.ticketCapacity = ticketCapacity;
    }

    public void setAgeLimit(AgeLimit ageLimit) {
        this.ageLimit = ageLimit;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
