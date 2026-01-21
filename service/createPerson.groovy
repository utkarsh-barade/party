import org.moqui.context.ExecutionContext

ExecutionContext ec = context.ec

// 1. ec.entity.find ka kaam he ki party table /entity me partyId dhundna
def party = ec.entity.find("moqui.party.Party")
        .condition("partyId", partyId)
        .one()

if (party == null) {
    ec.message.addError("Cannot create Person: Party with ID ${partyId} does not exist.")
    return
}

// 2. is jagah condition lagayi he ki partyTypeEnumId PERSON hona chahiye
if (party.partyTypeEnumId != "PERSON") {
    ec.message.addError("Cannot create Person: Party with ID ${partyId} is not of type PERSON.")
    return
}

/* 3. is jagah sql query chalayi he jo person table me naya record banayega
 insert into Person (partyId, firstName, lastName) values (?, ?, ?);
 aur create() method se naya record banega */

ec.entity.makeValue("moqui.party.Person")
        .setAll(context)
        .create()

// 4. ye bata raha he ki person successfully create ho gaya
context.response = "Person ${firstName} ${lastName} created successfully!"
