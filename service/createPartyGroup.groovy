import org.moqui.context.ExecutionContext

ExecutionContext ec = context.ec

def party = ec.entity.find("moqui.party.Party") .condition("partyId", partyId).one()

if (party == null) {
    ec.message.addError("Cannot create Party Group: Party with ID ${partyId} does not exist.")
    return
}
else if (party.partyTypeEnumId != "PARTY_GROUP") {
    ec.message.addError("Cannot create Party Group: Party with ID ${partyId} is not of type PARTY_GROUP.")
    return
}
// insert into PartyGroup (partyId, groupName, description) values (?, ?, ?);
ec.entity.makeValue("moqui.party.PartyGroup").setAll(context).create()

context.response = "PartyGroup ${groupName} ${description}  created successfully!"