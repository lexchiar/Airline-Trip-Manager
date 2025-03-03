export function SaveTrip(tripName, fileText, fileType){
    const file = new Blob([fileText], { type: "application/" + fileType});
    const link = document.createElement("a");
    const url = URL.createObjectURL(file);
    link.href = url;
    link.download = tripName + "." + fileType;
    document.body.appendChild(link);
    link.click();
    
    setTimeout(function() {
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);
    }, 0);
}