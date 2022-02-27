
describe('Unauthenticated User api health probe', () => {
    context('GET /api/user/health', () => {
        it('should return a anonymous health response', () => {
            cy.request({
                method: 'GET',
                url: '/api/user/health'
            })
                .should((response) => {
                    expect(response.status).to.eq(200)
                    expect(response.body).to.eq("Alive!")
                });
        });
    });
});