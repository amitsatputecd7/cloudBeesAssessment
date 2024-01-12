package com.assessment.trainapp;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.Seat;
import com.model.User;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/train")
public class TrainController {

    private final Map<User, Seat> ticketMap = new HashMap<>();


    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseTicket(@RequestBody User user, @RequestParam String section) {
        Seat seat = allocateSeat(section);
        
        ticketMap.put(user, seat);
        return ResponseEntity.ok("Ticket purchased successfully");
    }

    @GetMapping("/receipt")
    public ResponseEntity<String> viewReceipt(@RequestParam String email) {
        User user = findUserByEmail(email);
        if (user != null && ticketMap.containsKey(user)) {
            Seat seat = ticketMap.get(user);
            return ResponseEntity.ok(generateReceipt(user, seat.getSection()));
        } else {
            return ResponseEntity.badRequest().body("User not found ..");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<String> viewUsersBySection(@RequestParam String section) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<User, Seat> entry : ticketMap.entrySet()) {
            if (entry.getValue().getSection().equals(section)) {
                result.append(entry.getKey().getFullName())
                        .append(" - Section: ")
                        .append(entry.getValue().getSection())
                        .append(", Seat: ")
                        .append(entry.getValue().getSeatNumber())
                        .append("\n");
            }
        }
        return ResponseEntity.ok(result.toString());
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeUser(@RequestParam String email) {
        User user = findUserByEmail(email);
        if (user != null) {
            Seat removedSeat = ticketMap.remove(user);
            return ResponseEntity.ok("User " + user.getFullName() + " removed from Section " + removedSeat.getSection());
        } else {
            return ResponseEntity.badRequest().body("User not found.");
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<String> modifyUserSeat(@RequestParam String email, @RequestParam String newSection) {
        User user = findUserByEmail(email);
        if (user != null && ticketMap.containsKey(user)) {
            Seat currentSeat = ticketMap.get(user);
            ticketMap.put(user, new Seat(newSection, currentSeat.getSeatNumber()));
            return ResponseEntity.ok("User " + user.getFullName() + "'s seat modified to Section " + newSection);
        } else {
            return ResponseEntity.badRequest().body("User not found or has not purchased a ticket.");
        }
    }

    private Seat allocateSeat(String section) {
        // For simplicity, just alternates between section A and B
        int seatNumber = ticketMap.size() % 2 + 1;
        System.out.println(seatNumber);
        return new Seat(section, seatNumber);
    }

    private User findUserByEmail(String email) {
        return ticketMap.keySet().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    private String generateReceipt(User user, String section) {
        return "Receipt:\n" +
                "From: London\n" +
                "To: France\n" +
                "User: " + user.getFullName() + "\n" +
                "Email: " + user.getEmail() + "\n" +
                "Section: " + section + "\n" +
                "Seat: " + ticketMap.get(user).getSeatNumber() + "\n" +
                "Price Paid: $20";
    }
}
